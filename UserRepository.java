
package com.cineverse.cineverse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cineverse.cineverse.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

}
