package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class GroupDto {

    private final String drawDate;
    private final boolean isDrawn;
    private final boolean countChildren;
    private final BigDecimal giftValue;
    private final BigDecimal childGiftValue;
    private final String collectorContact;

}
