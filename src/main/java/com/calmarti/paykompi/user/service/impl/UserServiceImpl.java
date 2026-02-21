package com.calmarti.paykompi.user.service.impl;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
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
    private UserRepository userRepository;
    //private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository /*PasswordEncoder passwordEncoder*/){
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto createUser(CreateUserRequestDto request) {
        //validate username, email uniqueness constraint
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exist: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exist: " + request.email());
        }

        //map dto to entity
        User user = UserMapper.toEntity(request);
        //hash password, add it to entity
        //String hash = passwordEncoder.encode(request.password());
        user.setPasswordHash(request.password());
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        return UserMapper.toResponse(user);
    }

}
