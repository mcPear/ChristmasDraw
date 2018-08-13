package com;


import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.User;
import com.dto.GroupSimpleDto;
import com.mapper.GroupMapper;
import com.service.GroupService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GroupServiceTests {
    private
    @Mock
    GroupDao groupDao;
    @Mock
    private
    UserDao userDao;
    @Mock
    MembershipDao membershipDao;
    @InjectMocks
    private
    GroupService groupService;
    private KeycloakPrincipal keycloakPrincipal;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        keycloakPrincipal = new KeycloakPrincipal("something", new KeycloakSecurityContext());
        User user = new User(1L,"test","Artur", "Walega", "blah@gmail.com",
                "Something About", 0, false,"pl", null,
                null);
        when(userDao.findByPreferredUsername("mock")).thenReturn(user);
    }

    @Test
    public void TestGroupAdd(){
        assertEquals(true, groupService.add("Grupa1", keycloakPrincipal));
    }

    @Test
    public void TestGetAllGroup(){
        Group group1 = new Group("Grupa1");
        Group group2 = new Group("Grupa2");
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        when(groupDao.findAll()).thenReturn(groups);
        assertEquals(2, groupService.getAll(keycloakPrincipal).size());
    }

    @Test
    public void TestGetOneSimpleDto(){
        when(groupDao.findByName("test1")).thenReturn(new Group("test1"));
        assertNotNull(groupService.getOneSimpleDto("test1"));
        assertEquals(null, groupService.getOneSimpleDto("test2"));
        assertEquals("test1",groupService.getOneSimpleDto("test1").getName());
    }

    @Test
    public void TestExistGroup(){
        when(groupDao.findByName("test1")).thenReturn(new Group("test1"));
        assertTrue(groupService.existsGroup("test1"));
        assertFalse(groupService.existsGroup(""));
        assertFalse(groupService.existsGroup("null"));
    }

    @Test
    public void TestGetOneDto(){
        Group group = new Group("test1");
        when(groupDao.findByName("test1")).thenReturn(group);
        assertNotNull(groupService.getOneSimpleDto("test1"));
        assertEquals(GroupMapper.toDto(group), groupService.getOneDto("test1"));
        assertNull(groupService.getOneDto("test2"));
    }
}
