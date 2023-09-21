package com.fitplanner.user.service;

import com.fitplanner.user.exception.model.UserNotFoundException;
import com.fitplanner.user.model.api.ChangePasswordRequest;
import com.fitplanner.user.model.api.ConfirmationResponse;
import com.fitplanner.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ConfirmationResponse changePassword(ChangePasswordRequest request) {
        var user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new UserNotFoundException("User not found."));

        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        return new ConfirmationResponse("Password has been changed.");
    }
}
