package com.connectbundle.connect.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.model.OTP;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.OtpRepository;
import com.connectbundle.connect.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    private int generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        int otp = secureRandom.nextInt(90000) + 10000;
        return otp;
    }

    private void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        emailSender.send(message);
    }

    public Object[] sendOtp(String to) {
        Optional<User> optionalUser = userRepository.findByEmail(to);
        if (!optionalUser.isPresent()) {
            return new Object[] { false, "User with this email id does not exist" };
        }
        try {
            Optional<OTP> optionalOtp = otpRepository.findByEmail(to);
            if (optionalOtp.isPresent()) {
                OTP existingOtp = optionalOtp.get();
                if (!existingOtp.isExpired()) {
                    return new Object[] { false, "An otp has already been sent and is still valid" };
                } else {
                    otpRepository.deleteByEmail(to);
                }
            }
            int otp = generateOtp();
            String htmlContent = "<html>"
                    + "<body>"
                    + "<h1>Connect OTP Verification</h1>"
                    + "<p>Dear User,</p>"
                    + "<p>Your OTP for verification is: <strong>" + otp + "</strong></p>"
                    + "<p>This OTP is valid for 5 minutes.</p>"
                    + "<p>Thank you,<br/>Connect Team</p>"
                    + "</body>"
                    + "</html>";
            sendEmail(to, "OTP for Connect", htmlContent);
            try {
                OTP newOtp = new OTP();
                newOtp.setEmail(to);
                newOtp.setOtp(otp);
                newOtp.setCreatedAt(LocalDateTime.now());
                otpRepository.save(newOtp);
                return new Object[] { true, "Otp sent successfully" };
            } catch (Exception e) {
                return new Object[] { false, e.getMessage() };
            }
        } catch (MessagingException e) {
            return new Object[] { false, e.getMessage() };
        }
    }

    public Object[] validateOtp(String email, int otp) {
        try {
            Optional<OTP> optionalOtp = otpRepository.findByEmail(email);
            if (!optionalOtp.isPresent()) {
                return new Object[] { false, "No record of OTP being sent" };
            }
            OTP otpRecord = optionalOtp.get();
            if (otpRecord.isExpired()) {
                return new Object[] { false, "Invalid/Expired OTP" };
            }
            if (otpRecord.getOtp() == otp) {
                otpRepository.deleteByEmail(email);
                return new Object[] { true, "OTP validated Successfully" };
            }
            return new Object[] { false, "Invalid/Expired OTP" };
        } catch (Exception e) {
            return new Object[] { false, e.getMessage() };
        }
    }

}
