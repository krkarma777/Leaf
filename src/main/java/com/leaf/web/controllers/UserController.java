package com.leaf.web.controllers;

import com.leaf.domain.dtos.UserCreateRequestDTO;
import com.leaf.domain.entities.User;
import com.leaf.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequestDTO requestDTO) {
        userService.register(requestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok().body(user);
    }
}
