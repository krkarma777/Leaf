package com.leaf.domain.dtos.user;

import com.leaf.domain.entities.User;
import com.leaf.domain.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String email;
    private UserRole role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();

    }

    public UserResponseDTO() {
    }
}
