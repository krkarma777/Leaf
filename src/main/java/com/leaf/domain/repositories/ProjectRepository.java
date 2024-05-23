package com.leaf.domain.repositories;

import com.leaf.domain.entities.Project;
import com.leaf.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN p.teamMembers tm WHERE tm = :user OR p.projectManager = :user")
    Page<Project> findProjectsByUser(@Param("user") User user, Pageable pageable);
}
