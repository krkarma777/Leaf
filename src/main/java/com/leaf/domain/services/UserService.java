package com.leaf.domain.services;

import com.leaf.domain.dtos.UserCreateRequestDTO;
import com.leaf.domain.entities.User;
import com.leaf.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User register(UserCreateRequestDTO requestDTO) {
        String encodedPW = bCryptPasswordEncoder.encode(requestDTO.getPassword());
        requestDTO.setPassword(encodedPW);
        User user = new User(requestDTO);
        return userRepository.save(user);
    }
}
