
package com.cineverse.cineverse.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineverse.cineverse.entity.User;
import com.cineverse.cineverse.repository.UserRepository;
import com.cineverse.cineverse.service.OtpService;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*")
public class OtpController {

    private final OtpService otpService;
    private final UserRepository userRepository;

    public OtpController(
            OtpService otpService,
            UserRepository userRepository) {
        this.otpService = otpService;
        this.userRepository = userRepository;
    }

    // Send OTP
    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(
            @RequestBody Map<String, String> request) {

        String phoneNumber = request.get("phoneNumber");

        if (phoneNumber == null ||
                !phoneNumber.matches("[0-9]{10}")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid mobile number"));
        }

        otpService.generateOtp(phoneNumber);

        return ResponseEntity.ok(
                Map.of("message", "OTP generated successfully"));
    }

    // Verify OTP and save user
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(
            @RequestBody Map<String, String> request) {

        String phoneNumber = request.get("phoneNumber");
        String otp = request.get("otp");
        String name = request.get("name");

        if (phoneNumber == null || otp == null || name == null
                || name.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message",
                            "Name, phone number and OTP required"));
        }

        if (!phoneNumber.matches("[0-9]{10}")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid mobile number"));
        }

        boolean verified = otpService.verifyOtp(phoneNumber, otp);

        if (!verified) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid or expired OTP"));
        }

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(User::new);

        user.setName(name.trim());
        user.setPhoneNumber(phoneNumber);
        user.setVerified(true);

        userRepository.save(user);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful",
                        "userId", user.getId(),
                        "name", user.getName(),
                        "phoneNumber", user.getPhoneNumber()
                ));
    }
}
