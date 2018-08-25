package com.dao;

import com.domain.GiftPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftPartDao extends JpaRepository<GiftPart, Long> {

    List<GiftPart> findByMembershipId(long membershipId);

}
