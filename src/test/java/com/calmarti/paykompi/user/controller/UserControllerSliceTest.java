package com.calmarti.paykompi.user.controller;


import com.calmarti.paykompi.domain.user.controller.UserController;
import com.calmarti.paykompi.domain.user.dto.UserResponseDto;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserRole;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.enums.UserType;
import com.calmarti.paykompi.domain.user.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = UserController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.REGEX,
                pattern = "com.calmarti.paykompi.config.security.*"
        )
)
public class UserControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Nested
    class getUserById {
        @Test
        void shouldReturn200WhenUserExists() throws Exception {

            //Test data
            //Requested user id and user response dto
            UUID id = UUID.fromString("595c397d-2b7d-496e-b1f0-3eabd6ab8d0f");
            UserResponseDto testUser = new UserResponseDto(
                    id,
                    "johndoe",
                    "johndoe@paykompi.com",
                    "John",
                    "Doe",
                    UserType.CUSTOMER,
                    UserStatus.ACTIVE,
                    Instant.parse("2026-03-21T18:37:06.239Z"),
                    Instant.parse("2026-03-21T18:37:06.239Z"));

            //Authenticated user
            User mockedAuthenticatedUser = new User();
            mockedAuthenticatedUser.setId(UUID.randomUUID());    //since we are abstracting away security auth user can be any user
            mockedAuthenticatedUser.setUserRole(UserRole.USER);

            //arrange - GIVEN
            given(userService.getUserById(eq(id), any(User.class)))  //since we are abstracting away security auth user can be any user
                    .willReturn(testUser);
            //act - (WHEN)
            mockMvc.perform(get("/api/v1/users/{id}", id)
                    .principal(new UsernamePasswordAuthenticationToken(mockedAuthenticatedUser, null)))
            //assert -(THEN)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(testUser.id().toString()))
                    .andExpect(jsonPath("$.username").value(testUser.username()))
                    .andExpect(jsonPath("$.email").value(testUser.email()))
                    .andExpect(jsonPath("$.firstName").value(testUser.firstName()))
                    .andExpect(jsonPath("$.lastName").value(testUser.lastName()))
                    .andExpect(jsonPath("$.userType").value(testUser.userType().toString()))
                                    .andExpect(jsonPath("$.userStatus").value(testUser.userStatus().toString()))
                    .andExpect(jsonPath("$.createdAt").value(testUser.createdAt().toString()))
                    .andExpect(jsonPath("$.updatedAt").value(testUser.updatedAt().toString()));
            //verify
            verify(userService).getUserById(eq(id), any(User.class));

        }
    }
}
