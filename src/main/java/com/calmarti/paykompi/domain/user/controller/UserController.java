package com.calmarti.paykompi.domain.user.controller;

import com.calmarti.paykompi.common.dto.CustomPage;
import com.calmarti.paykompi.domain.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserStatusDto;
import com.calmarti.paykompi.domain.user.dto.UserResponseDto;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.enums.UserType;
import com.calmarti.paykompi.domain.user.service.UserService;
import com.calmarti.paykompi.swagger.user.CreateUserDocs;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operations to manage users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //Public endpoint
    @PostMapping
    @CreateUserDocs
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto request){
        UserResponseDto userResponseDto = userService.createUser(request);
        return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    //restricted ONLY to own user
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id,  @AuthenticationPrincipal User auhtenticatedUser){
            UserResponseDto userResponseDto = userService.getUserById(id, auhtenticatedUser);
        return ResponseEntity.ok(userResponseDto);
    }


    //restricted ONLY to own user (Forbidden for role = ADMIN!)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable UUID id, @RequestBody UpdateUserRequestDto request,  @AuthenticationPrincipal User auhtenticatedUser){
        userService.updateUserById(id, request, auhtenticatedUser);
        return ResponseEntity.noContent().build();
    }

      //restricted to role = ADMIN
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateUserStatus(@PathVariable UUID id, @RequestBody @Valid UpdateUserStatusDto request){
        userService.changeUserStatus(id, request);
        return ResponseEntity.noContent().build();
    }

    //restricted to role = ADMIN
    @GetMapping
    ResponseEntity<CustomPage<UserResponseDto>> getAllUsers(
            @RequestParam(required = false) UserType userType,
            @RequestParam(required = false)UserStatus userStatus,
            Pageable pageable){
        CustomPage<UserResponseDto> response = userService.getAllUsers(userType, userStatus, pageable);
        return ResponseEntity.ok(response);
    }

    //restricted to own user and role = ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id, @AuthenticationPrincipal User auhtenticatedUser){
        userService.deleteUser(id, auhtenticatedUser);
        return ResponseEntity.noContent().build();
    }
}
