package com.mapper;

import com.domain.User;
import com.dto.UserDto;
import org.keycloak.representations.AccessToken;

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

    public static User toUser(AccessToken token) {
        return new User(
                null,
                token.getPreferredUsername(),
                token.getGivenName(),
                token.getFamilyName(),
                null,
                0,
                null,
                null
        );
    }

}
