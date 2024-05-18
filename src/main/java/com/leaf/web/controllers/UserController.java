package com.leaf.web.controllers;

import com.leaf.domain.dtos.UserCreateRequestDTO;
import com.leaf.domain.dtos.UserFindByQueryResponseDTO;
import com.leaf.domain.entities.User;
import com.leaf.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
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

    @GetMapping("/search-members")
    public ResponseEntity<?> searchMembers(@RequestParam("q") String q) {
        List<UserFindByQueryResponseDTO> list = userService.searchUsers(q).stream().map(UserFindByQueryResponseDTO::new).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmailExists(@RequestParam("email") String email) {
        boolean exists = userService.checkEmailExists(email);
        return ResponseEntity.ok().body(Map.of("exists", exists));
    }
}
