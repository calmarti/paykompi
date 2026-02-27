package com.calmarti.paykompi.auth;

import com.calmarti.paykompi.auth.dto.LoginRequestDto;
import com.calmarti.paykompi.auth.dto.LoginResponseDto;
import com.calmarti.paykompi.user.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto dto) {
        LoginResponseDto loginResponseDto = null;
        return loginResponseDto;
    }
}
