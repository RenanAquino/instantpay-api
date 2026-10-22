package com.instantpay.app.user.services;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instantpay.app.user.dtos.CreateUserDTO;
import com.instantpay.app.user.dtos.UpdateUserDTO;
import com.instantpay.app.user.dtos.UserDTO;
import com.instantpay.app.user.enums.AccountStatus;
import com.instantpay.app.user.models.User;
import com.instantpay.app.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserDTO(user);
    }

    @Override
    @Transactional
    public void createUser(CreateUserDTO data) {
        if (userRepository.existsByEmail(data.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(data.name());
        user.setEmail(data.email());
        user.setPassword(passwordEncoder.encode(data.password()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(UUID id, UpdateUserDTO data) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(data.name());
    }

    @Override
    @Transactional
    public void closeAccount(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountStatus(AccountStatus.CLOSED);
    }
    
    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAccountStatus()
        );
    }
}
