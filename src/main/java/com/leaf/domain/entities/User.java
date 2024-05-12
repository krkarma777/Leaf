package com.leaf.domain.entities;

import com.leaf.domain.dtos.UserCreateRequestDTO;
import com.leaf.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    public User(UserCreateRequestDTO requestDTO) {
        this.email = requestDTO.getEmail();
        this.password = requestDTO.getPassword();
    }
}
