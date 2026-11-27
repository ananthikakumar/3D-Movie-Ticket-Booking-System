
package com.cineverse.cineverse.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cineverse.cineverse.entity.OtpVerification;
import com.cineverse.cineverse.repository.OtpRepository;

@Service
public class OtpService {

    private final OtpRepository otpRepository;

    private final SecureRandom random = new SecureRandom();

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    // Generate OTP
    @Transactional
    public void generateOtp(String phoneNumber) {

        String otp = String.format("%06d",
                random.nextInt(1000000));

        // Delete previous OTP
        otpRepository.deleteByPhoneNumber(phoneNumber);

        OtpVerification verification = new OtpVerification();

        verification.setPhoneNumber(phoneNumber);
        verification.setOtp(otp);

        // OTP expires in 5 minutes
        verification.setExpiryTime(
                LocalDateTime.now().plusMinutes(5));

        otpRepository.save(verification);
    }

    // Verify OTP
    @Transactional
    public boolean verifyOtp(String phoneNumber, String enteredOtp) {

        OtpVerification verification = otpRepository
                .findByPhoneNumber(phoneNumber)
                .orElse(null);

        // Check OTP exists
        if (verification == null) {
            return false;
        }

        // Check expiry
        if (LocalDateTime.now().isAfter(
                verification.getExpiryTime())) {

            otpRepository.deleteByPhoneNumber(phoneNumber);
            return false;
        }

        // Check OTP
        if (!verification.getOtp().equals(enteredOtp)) {
            return false;
        }

        // Delete OTP after successful verification
        otpRepository.deleteByPhoneNumber(phoneNumber);

        return true;
    }
}
