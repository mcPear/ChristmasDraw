package com.mapper;

import com.domain.User;
import com.dto.UserDto;

public final class UserMapper {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getPreferredUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getAbout(),
                user.getChildren()
        );
    }

}
