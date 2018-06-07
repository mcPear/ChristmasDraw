package com.dao;

import com.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipDao extends JpaRepository<Membership, Long> {
    List<Membership> findByUserIdAndOwns(long userId, boolean owns);

    List<Membership> findByGroupId(long groupId);

    Optional<Membership> deleteByUserIdAndGroupId(long userId, long groupId);
}
