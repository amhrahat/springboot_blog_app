package com.rahat.blog.security;

import com.rahat.blog.mappers.TagMapper;
import com.rahat.blog.repositories.TagRepository;
import com.rahat.blog.services.CurrentUserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SendOTP {

    private final JavaMailSender mailSender;

    public SendOTP(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendOtpEmail(String email, String otp) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Email Verification OTP");
            message.setText("Your OTP is: " + otp + "\nExpires in 10 minutes.");
            mailSender.send(message);
    }


    public String generateOtp() {
        return String.valueOf(100000 + new SecureRandom().nextInt(900000));
    }

}
