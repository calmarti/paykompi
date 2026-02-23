package com.calmarti.paykompi.user.controller;

import com.calmarti.paykompi.user.dto.UserResponseDto;
import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.enums.UserType;
import com.calmarti.paykompi.user.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)   //avoids Security Spring
public class UserControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;


    @Nested
    class getUserById {
        @Test
        void shouldReturn200WhenUserExists() throws Exception {

            //test data
            UUID id = UUID.fromString("96a2c8ed-c6e7-4a1c-9a66-be867b0902f7");
            UserResponseDto testUser = new UserResponseDto(
                    id,
                    "johndoe",
                    "john.doe@example.com",
                    "John",
                    "Doe",
                    UserType.PERSONAL,
                    UserStatus.ACTIVE,
                    Instant.parse("2026-02-22T11:28:04.962Z"));

            //arrange
            given(userService.getUserById(id))
                    .willReturn(testUser);
            //act and assert
            mockMvc.perform(get("/api/v1/users/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(testUser.id().toString()))
                    .andExpect(jsonPath("$.username").value(testUser.username()))
                    .andExpect(jsonPath("$.email").value(testUser.email()))
                    .andExpect(jsonPath("$.firstName").value(testUser.firstName()))
                    .andExpect(jsonPath("$.lastName").value(testUser.lastName()))
                    .andExpect(jsonPath("$.userType").value(testUser.userType().toString()))
                    .andExpect(jsonPath("$.userStatus").value(testUser.userStatus().toString()))
                    .andExpect(jsonPath("$.createdAt").value(testUser.createdAt().toString()));
            //verify
            verify(userService).getUserById(id);

        }
    }
}
