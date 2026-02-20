package com.calmarti.paykompi.user.service.impl;

import com.calmarti.paykompi.user.dto.UserResponseDto;
import com.calmarti.paykompi.user.repository.UserRepository;
import com.calmarti.paykompi.user.service.UserService;

import java.util.UUID;


public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        userRepository.findById(id);
        return null;
    }

}
