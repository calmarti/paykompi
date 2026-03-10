package com.calmarti.paykompi.domain.user.service.impl;

import com.calmarti.paykompi.common.exception.BusinessRuleViolationException;
import com.calmarti.paykompi.common.exception.CustomAccessDeniedException;
import com.calmarti.paykompi.common.exception.DuplicateResourceException;
import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.domain.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.domain.user.dto.UpdateUserStatusDto;
import com.calmarti.paykompi.domain.user.dto.UserResponseDto;
import com.calmarti.paykompi.domain.user.entity.User;
import com.calmarti.paykompi.domain.user.enums.UserRole;
import com.calmarti.paykompi.domain.user.enums.UserStatus;
import com.calmarti.paykompi.domain.user.mapper.UserMapper;
import com.calmarti.paykompi.domain.user.repository.UserRepository;
import com.calmarti.paykompi.domain.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto createUser(CreateUserRequestDto dto) {
        //validate username, email uniqueness constraints
        if (userRepository.existsByUsername(dto.username())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        //map dto to entity
        User user = UserMapper.toEntity(dto);
        //hash password, add it to entity
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
        user.setUserRole(UserRole.USER);
        user.setUserStatus(UserStatus.ACTIVE);
        //save to DB
        userRepository.save(user);
        //map entity to response and return it
        return UserMapper.toResponse(user);
    }


    @Override
    public UserResponseDto getUserById(UUID id, User authenticatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getId().equals(authenticatedUser.getId())){
            throw new CustomAccessDeniedException("Param userId does not match authenticated userId");
        }
        return UserMapper.toResponse(user);
    }

    @Override
    public void updateUserById(UUID id, UpdateUserRequestDto dto, User authenticatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getId().equals(authenticatedUser.getId())){
            throw new CustomAccessDeniedException("Id param does not match authenticated user id");
        }
        UserMapper.updateEntity(user, dto);
        userRepository.save(user);
    }

    @Override
    public void changeUserStatus(UUID id, UpdateUserStatusDto dto) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserMapper.updateUserStatusInEntity(user, dto);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id, User authenticatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.getId().equals(authenticatedUser.getId()) && ! authenticatedUser.getUserRole().equals(UserRole.ADMIN)){
            throw new CustomAccessDeniedException("Id param does not match authenticated user id");
        }
        userRepository.deleteById(id);
    }
}