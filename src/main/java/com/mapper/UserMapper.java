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
                user.getEmail(),
                user.getAbout(),
                user.getChildren(),
                include,
                user.getVirtual(),
                user.getLang()
        );
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getPreferredUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAbout(),
                user.getChildren(),
                user.getVirtual(),
                user.getLang()
        );
    }

    public static User toUser(AccessToken token, String lang) {
        return new User(
                null,
                token.getPreferredUsername(),
                token.getGivenName(),
                token.getFamilyName(),
                token.getEmail(),
                null,
                0,
                false,
                lang,
                null,
                null
        );
    }

    public static User toUser(UserDto dto, Long maxId) {
        return new User(
                null,
                getLogin(dto, maxId),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getAbout(),
                dto.getChildren(),
                true,
                dto.getLang(),
                null,
                null
        );
    }

    private static String getLogin(UserDto dto, Long maxId) {
        return deAccent(dto.getFirstName().toLowerCase()) + (maxId + 1);
    }

    private static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

}
