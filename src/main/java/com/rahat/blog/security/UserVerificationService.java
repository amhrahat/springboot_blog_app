package com.rahat.blog.security;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UserVerificationService {

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public UserVerificationService(JavaMailSender mailSender,
                                    PasswordEncoder passwordEncoder) {
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }
    public void sendOtpEmail(String email, String otp) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Email Verification OTP");
            message.setText("Your OTP is: " + otp + "\nExpires in 5 minutes.");
            mailSender.send(message);
    }


    public String generateOtp() {
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }

    public String hashOtp(String otp) {
        return passwordEncoder.encode(otp);
    }

}
