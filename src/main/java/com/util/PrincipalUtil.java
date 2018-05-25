package com.util;

import com.dao.UserDao;
import com.domain.User;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;

public class PrincipalUtil {

    private static String getPreferredUsername(KeycloakPrincipal principal) {
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        String result;
        try {
            result = token.getPreferredUsername();
        }catch (Exception ex){
            System.out.println("Can't find user from token!");
            return "mock";
        }
        return result;
    }

    public static User getUserByPrincipal(KeycloakPrincipal principal, UserDao userDao) {
        String preferredUsername = getPreferredUsername(principal);
        return userDao.findByPreferredUsername(preferredUsername);
    }

}
