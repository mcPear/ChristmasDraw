package com.mapper;

import com.domain.GiftPart;
import com.domain.Membership;
import com.dto.GiftPartDto;

public final class GiftPartMapper {

    public static GiftPartDto toDto(GiftPart giftPart) {
        return new GiftPartDto(
                giftPart.getId(),
                giftPart.getName(),
                giftPart.getValue(),
                giftPart.getMembership().getId()
        );
    }

    public static GiftPart toGiftPart(GiftPartDto dto, Membership membership) {
        return new GiftPart(
                null,
                dto.getName(),
                dto.getValue(),
                membership
        );
    }

}
