package com.leaf.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leaf.domain.dtos.project.ProjectCreateRequestDTO;
import com.leaf.domain.entities.Project;
import com.leaf.domain.entities.User;
import com.leaf.domain.enums.PriorityType;
import com.leaf.domain.enums.Status;
import com.leaf.domain.repositories.ProjectRepository;
import com.leaf.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private User manager;

    @BeforeEach
    void setUp() {
        projectRepository.deleteAll();
        userRepository.deleteAll();

        manager = new User("manager@example.com", "test");
        manager = userRepository.save(manager);
    }

    @Test
    @WithMockUser(username = "manager@example.com")
    void testCreateProject_ValidInput() throws Exception {

        ProjectCreateRequestDTO requestDTO = new ProjectCreateRequestDTO(
                "New Project", "Description of the project",
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10),
                List.of(manager.getId()), "Software Development",
                PriorityType.MEDIUM, Status.PLANNING);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        List<Project> projects = projectRepository.findAll();
        assertEquals(1, projects.size());
        assertEquals("New Project", projects.get(0).getProjectName());
    }
}
