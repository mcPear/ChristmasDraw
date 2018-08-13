package com;

import com.dao.UserDao;
import com.domain.User;
import com.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Mock
    private
    UserDao userDao;
    @InjectMocks
    private
    UserService userService;
    private KeycloakPrincipal keycloakPrincipal;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        keycloakPrincipal = new KeycloakPrincipal("something", new KeycloakSecurityContext());
        User user = new User(1L, "test1", "Artur", "Walega", "blah@gmail.com",
                "Something About", 0, false,"pl", null, null);
        when(userDao.findByPreferredUsername("test1")).thenReturn(user);
    }

    @Test
    public void TestGetOneDto() {

//        assertNull(userService.getOneDto("whatever", keycloakPrincipal, null));
//        assertNotNull(userService.getOneDto("test1", keycloakPrincipal));
    }

    @Test
    public void TestFinAll() {
        List<User> users = new ArrayList<>();
        User user1 = new User(1L, "test1", "Artur", "Walega", "blah@gmail.com",
                "Something About", 0, false,"pl", null, null);
        User user2 = new User(2L, "test2", "Michał", "Walega", "blah@gmail.com",
                "Something About", 0, false,"pl", null, null);
        users.add(user1);
        users.add(user2);
        when(userDao.findAll()).thenReturn(users);
        assertNotNull(userService.getAll());
        assertEquals(2, userService.getAll().size());
        assertNotEquals(3, userService.getAll());
    }
}
