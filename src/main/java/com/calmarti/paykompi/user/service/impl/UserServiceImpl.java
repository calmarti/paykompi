package com.calmarti.paykompi.user.service.impl;

import com.calmarti.paykompi.common.exception.DuplicateResourceException;
import com.calmarti.paykompi.common.exception.ResourceNotFoundException;
import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.dto.UpdateUserRequestDto;
import com.calmarti.paykompi.user.dto.UserResponseDto;
import com.calmarti.paykompi.user.entity.User;
import com.calmarti.paykompi.user.enums.UserRole;
import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.mapper.UserMapper;
import com.calmarti.paykompi.user.repository.UserRepository;
import com.calmarti.paykompi.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository /*PasswordEncoder passwordEncoder*/){
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto createUser(CreateUserRequestDto dto) {
        //validate username, email uniqueness constraint
        if (userRepository.existsByUsername(dto.username())) {
             throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email already exists");
        }

        //map dto to entity
        User user = UserMapper.toEntity(dto);
        //hash password, add it to entity
        //String hash = passwordEncoder.encode(request.password());
        user.setPasswordHash(dto.password());
        user.setUserRole(UserRole.USER);
        user.setUserStatus(UserStatus.ACTIVE);
        //save to DB
        userRepository.save(user);
        //map entity to response and return it
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return UserMapper.toResponse(user);
    }

    @Override
    public void updateUserById(UUID id, UpdateUserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        UserMapper.updateEntity(user, dto);
        userRepository.save(user);
    }

}
