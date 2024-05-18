package com.leaf.domain.dtos.user;

import com.leaf.domain.entities.User;
import lombok.Data;

@Data
public class UserFindByQueryResponseDTO {

    private Long id;
    private String email;

    public UserFindByQueryResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
