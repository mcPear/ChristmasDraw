package com.service;

import com.dao.GiftPartDao;
import com.dao.MembershipDao;
import com.domain.GiftPart;
import com.domain.Membership;
import com.dto.GiftPartDto;
import com.mapper.GiftPartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftPartService {

    private final MembershipDao membershipDao;
    private final GiftPartDao giftPartDao;

    @Autowired
    public GiftPartService(MembershipDao membershipDao, GiftPartDao giftPartDao) {
        this.membershipDao = membershipDao;
        this.giftPartDao = giftPartDao;
    }

    public List<GiftPartDto> getAll(Long membershipId) {
        return giftPartDao.findByMembershipId(membershipId).stream()
                .map(GiftPartMapper::toDto).collect(Collectors.toList());
    }

    public void add(GiftPartDto dto, Long membershipId) {
        Membership membership = membershipDao.getOne(membershipId);
        giftPartDao.save(GiftPartMapper.toGiftPart(dto, membership));
    }

    public void update(GiftPartDto dto) {
        GiftPart toBeUpdated = giftPartDao.findOne(dto.getId());
        toBeUpdated.setName(dto.getName());
        toBeUpdated.setValue(dto.getValue());
        giftPartDao.save(toBeUpdated);
    }

    public void delete(Long id) {
        giftPartDao.delete(id);
    }

}
