
package com.cineverse.cineverse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cineverse.cineverse.entity.User;
import com.cineverse.cineverse.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Mobile number already registered!");
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
