package com.leaf.domain.repositories;

import com.leaf.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM users u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%',:query,'%'))")
    List<User> searchByName(@Param("query") String query);
}
