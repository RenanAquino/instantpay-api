package com.instantpay.app.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instantpay.app.user.dtos.CreateUserDTO;
import com.instantpay.app.user.dtos.UpdateUserDTO;
import com.instantpay.app.user.dtos.UserDTO;
import com.instantpay.app.user.models.User;
import com.instantpay.app.user.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserDTO> findById(@AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.findById(user.getId());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO data) {
        UserDTO userDTO = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal User user, @Valid @RequestBody UpdateUserDTO data) {
        userService.updateUser(user.getId(), data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/close")
    public ResponseEntity<Void> closeAccount(@AuthenticationPrincipal User user) {
        userService.closeAccount(user.getId());
        return ResponseEntity.noContent().build();
    }
}
