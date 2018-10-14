package com.service;

import com.dao.GiftPartDao;
import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import com.dto.GiftPartDto;
import com.mapper.GiftPartMapper;
import com.util.PrincipalUtil;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftPartService {

    private final MembershipDao membershipDao;
    private final GiftPartDao giftPartDao;
    private final GroupDao groupDao;
    private final UserDao userDao;

    @Autowired
    public GiftPartService(MembershipDao membershipDao, GiftPartDao giftPartDao, GroupDao groupDao, UserDao userDao) {
        this.membershipDao = membershipDao;
        this.giftPartDao = giftPartDao;
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    public List<GiftPartDto> getAll(String group, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        Group groupObj = groupDao.findByName(group);
        Membership membership = membershipDao
                .findByGroupId(groupObj.getId())
                .stream()
                .filter(m -> user.getPreferredUsername().equals(m.getUser().getPreferredUsername()))
                .collect(Collectors.toList()).get(0);
        return giftPartDao.findByMembershipId(membership.getId()).stream()
                .map(GiftPartMapper::toDto).collect(Collectors.toList());
    }

    public void add(GiftPartDto dto) {
        Membership membership = membershipDao.getOne(dto.getMembershipId());
        giftPartDao.save(GiftPartMapper.toGiftPart(dto, membership));
    }

    public void update(List<GiftPartDto> dtos, String groupName, KeycloakPrincipal principal) {
        giftPartDao.deleteAll();
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        Group group = groupDao.findByName(groupName);
        List<Membership> memberships = membershipDao.findByGroupId(group.getId());
        Membership membership = memberships.stream().filter(m -> m.getUser().getId() == user.getId()).findFirst().get();
        dtos.stream().map(dto -> GiftPartMapper.toGiftPart(dto, membership)).forEach(giftPartDao::save);
    }

    public void delete(Long id) {
        giftPartDao.delete(id);
    }

}
