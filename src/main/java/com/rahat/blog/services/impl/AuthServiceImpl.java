package com.rahat.blog.services.impl;

import com.rahat.blog.domain.dtos.LoginResponseDto;
import com.rahat.blog.domain.dtos.RegResponseDto;
import com.rahat.blog.domain.entities.User;
import com.rahat.blog.domain.entities.VerificationToken;
import com.rahat.blog.repositories.UserRepository;
import com.rahat.blog.repositories.VerificationTokenRepository;
import com.rahat.blog.security.JwtTokenProvider;
import com.rahat.blog.security.UserVerificationService;
import com.rahat.blog.services.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserVerificationService userVerificationService;
    private final VerificationTokenRepository verificationTokenRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           UserVerificationService userVerificationService,
                           VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userVerificationService = userVerificationService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public RegResponseDto registration(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already exists");
        }
        String otp = userVerificationService.generateOtp();
        VerificationToken token = new VerificationToken();
        String hashedOtp = userVerificationService.hashOtp(otp);
        token.setEmail(email);
        token.setOtpHash(hashedOtp);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(2));
        userVerificationService.sendOtpEmail(email, otp);
        verificationTokenRepository.save(token);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setVerified(false);

        userRepository.save(user);
        return new RegResponseDto(user.getName(), user.getEmail());
    }

    @Override
    public LoginResponseDto login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!user.isVerified()) {
            throw new DisabledException("Email not verified");
        }
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(email, password);

        authenticationManager.authenticate(authentication);

        String token = jwtTokenProvider.generateToken(email);
        long expiresIn = jwtTokenProvider.getExpirationTime();

        return new LoginResponseDto(token, "Bearer", expiresIn, email);
    }

    @Override
    public void verifyOtp(String email, String otp) {
        VerificationToken token = verificationTokenRepository.findLatestByEmail(email)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!passwordEncoder.matches(otp, token.getOtpHash()))
            throw new RuntimeException("Invalid OTP");

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        user.setVerified(true);

        userRepository.save(user);
        verificationTokenRepository.deleteById(token.getId());
    }

    @Scheduled(fixedRate = 600000)
    @Transactional
    public void deleteExpiredOtpsUsers() {

        LocalDateTime now = LocalDateTime.now();

        List<String> expiredEmails =
                verificationTokenRepository.findExpiredEmails(now);

        if (!expiredEmails.isEmpty()) {
            userRepository.deleteUnverifiedUsersByEmailIn(expiredEmails);
        }

        verificationTokenRepository.deleteAllExpired(now);
    }
}