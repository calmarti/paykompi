package com.calmarti.paykompi.user.service.impl;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.dto.UserResponseDto;
import com.calmarti.paykompi.user.entity.User;
import com.calmarti.paykompi.user.mapper.UserMapper;
import com.calmarti.paykompi.user.repository.UserRepository;
import com.calmarti.paykompi.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto createUser(CreateUserRequestDto request) {
        User user = UserMapper.toEntity(request);
        //hash password, add it to user
        //set system/business-related fields
        //save to DB
        //return responseDto
        return null;
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        //userRepository.findById(id);
        return null;
    }

}
