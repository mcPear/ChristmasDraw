package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GroupDto {

    private String drawDate;
    private boolean isDrawn;
    private boolean countChildren;
    private BigDecimal giftValue;
    private BigDecimal childGiftValue;
    private BigDecimal calculatedChildGiftValue;
    private String collectorContact;

}
