package com.calmarti.paykompi.auth;

import com.calmarti.paykompi.auth.dto.LoginRequestDto;
import com.calmarti.paykompi.auth.dto.LoginResponseDto;
import com.calmarti.paykompi.config.security.JwtService;
import com.calmarti.paykompi.user.entity.User;
import com.calmarti.paykompi.user.enums.UserStatus;
import com.calmarti.paykompi.user.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        if (user.getUserStatus() != UserStatus.ACTIVE) {
            throw new BadCredentialsException("User is not active");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponseDto(token, "Bearer");
    }
}
