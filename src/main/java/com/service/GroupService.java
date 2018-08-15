package com.service;

import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import com.dto.GroupDto;
import com.dto.GroupSimpleDto;
import com.mapper.GroupMapper;
import com.util.PrincipalUtil;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private UserDao userDao;
    private GroupDao groupDao;
    private MembershipDao membershipDao;

    @Autowired
    public GroupService(UserDao userDao, GroupDao groupDao, MembershipDao membershipDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.membershipDao = membershipDao;
    }

    public Boolean existsGroup(String name) {
        return groupDao.findByName(name) != null;
    }

    public GroupDto getOneDto(String name) {
        return GroupMapper.toDto(groupDao.findByName(name));
    }

    public GroupSimpleDto getOneSimpleDto(String name) {
        Group item = groupDao.findByName(name);
        if (item != null)
            return new GroupSimpleDto(item.getId(),
                    item.getName(),
                    (item.getDrawDate() != null ? item.getDrawDate().getTime() : 0),
                    item.isDrawn(),
                    item.isCountChildren(),
                    item.getGiftValue(),
                    item.getChildGiftValue(),
                    item.getCalculatedChildGiftValue(),
                    item.getCollectorContact());
        else return null;
    }

    public List<GroupSimpleDto> getAll(KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        if (user != null) {
            List<Group> list = groupDao.findAll();
            List<GroupSimpleDto> dtoList = new ArrayList<>();
            for (Group item : list) {
                dtoList.add(new GroupSimpleDto(item.getId(),
                        item.getName(),
                        (item.getDrawDate() != null ? item.getDrawDate().getTime() : 0),
                        item.isDrawn(),
                        item.isCountChildren(),
                        item.getGiftValue(),
                        item.getChildGiftValue(),
                        item.getCalculatedChildGiftValue(),
                        item.getCollectorContact()));
            }
            return dtoList;
        } else {
            throw new IllegalArgumentException("Something is not quite right");
        }
    }

    public void delete(String groupName, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        if (user != null) {
            Group group = groupDao.findByName(groupName);
            groupDao.delete(group.getId());
        } else {
            throw new IllegalArgumentException("Something is not quite right");
        }
    }

    public Boolean add(String groupName, KeycloakPrincipal principal) {
        Group storedGroup = groupDao.findByName(groupName);
        if (storedGroup == null) {
            Group createdGroup = groupDao.save(new Group(groupName));
            User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
            membershipDao.save(new Membership(null, true, true,
                    false, true,
                    user.getAbout(), user.getChildren(), createdGroup,
                    user, null, null));
            return true;
        } else {
            return false;
        }
    }

    public void update(GroupSimpleDto group) {
        if (group != null && !group.getName().isEmpty()) {
            Group groupDb = groupDao.findByName(group.getName());
            groupDb.setDrawn(group.isDrawn());
            groupDb.setDrawDate(new Date(group.getDrawDate()));
            groupDb.setGiftValue(group.getGiftValue());
            groupDb.setCountChildren(group.isCountChildren());
            groupDb.setChildGiftValue(group.getChildGiftValue());
            groupDb.setCollectorContact(group.getCollectorContact());

            groupDao.save(groupDb);
        }
    }

}
