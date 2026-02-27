package com.calmarti.paykompi.auth;

import com.calmarti.paykompi.auth.dto.LoginRequestDto;
import com.calmarti.paykompi.auth.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto loginUser(LoginRequestDto dto);
}
