package com.calmarti.paykompi.user.mapper;

import com.calmarti.paykompi.user.dto.CreateUserRequestDto;
import com.calmarti.paykompi.user.entity.User;

public class UserMapper {
    public static User toEntity(CreateUserRequestDto dto){
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        return user;
   }
}
