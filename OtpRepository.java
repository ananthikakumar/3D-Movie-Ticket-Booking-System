
package com.cineverse.cineverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cineverse.cineverse.entity.OtpVerification;

public interface OtpRepository
        extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);
}
