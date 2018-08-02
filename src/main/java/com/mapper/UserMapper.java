package com.mapper;

import com.domain.User;
import com.dto.UserDto;
import com.dto.UserIncludeDto;
import org.keycloak.representations.AccessToken;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class UserMapper {

    public static UserIncludeDto toIncludeDto(User user, boolean include) {
        System.out.println(include);
        return new UserIncludeDto(
                user.getId(),
                user.getPreferredUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getAbout(),
                user.getChildren(),
                include
        );
    }

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

    public static User toUser(UserDto dto, Long maxId) {
        return new User(
                null,
                getLogin(dto, maxId), //todo use sequence
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAbout(),
                dto.getChildren(),
                null,
                null
        );
    }

    private static String getLogin(UserDto dto, Long maxId) {
        return deAccent(dto.getFirstName().toLowerCase()) + maxId;
    }

    private static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

}
