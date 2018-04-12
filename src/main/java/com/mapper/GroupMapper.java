package com.mapper;

import com.domain.Group;
import com.dto.GroupDto;

public final class GroupMapper {

    public static GroupDto toDto(Group group) {
        return new GroupDto(
                group.getDrawDate() != null ? group.getDrawDate().toString() : null,
                group.isDrawn(),
                group.isCountChildren(),
                group.getGiftValue(),
                group.getChildGiftValue(),
                group.getCollectorContact()
        );
    }

}
