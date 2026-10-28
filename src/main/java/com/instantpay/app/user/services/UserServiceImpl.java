package com.instantpay.app.user.services;

import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instantpay.app.user.dtos.CreateUserDTO;
import com.instantpay.app.user.dtos.UpdateUserDTO;
import com.instantpay.app.user.dtos.UserDTO;
import com.instantpay.app.user.enums.AccountStatus;
import com.instantpay.app.user.exceptions.ClosedAccountException;
import com.instantpay.app.user.exceptions.UserNotFoundException;
import com.instantpay.app.user.models.User;
import com.instantpay.app.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return mapToUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(CreateUserDTO data) {
        if (userRepository.existsByEmail(data.email().trim().toLowerCase())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        User user = new User();
        user.setName(data.name());
        user.setEmail(data.email().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(data.password()));
        userRepository.save(user);
        return mapToUserDTO(user);
    }

    @Override
    @Transactional
    public void updateUser(UUID id, UpdateUserDTO data) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        if (isClosedAccount(user)) {
            throw new ClosedAccountException();
        }
        user.setName(data.name());
    }

    @Override
    @Transactional
    public void closeAccount(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        if (isClosedAccount(user)) {
            throw new ClosedAccountException();
        }
        user.setAccountStatus(AccountStatus.CLOSED);
    }

    private boolean isClosedAccount(User user) {
        return user.getAccountStatus().equals(AccountStatus.CLOSED);
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
