package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GroupSimpleDto {
    private Long id;
    private String name;
    private Long drawDate;
    private boolean isDrawn;
    private boolean countChildren;
    private BigDecimal giftValue;
    private BigDecimal childGiftValue;
    private BigDecimal calculatedChildGiftValue;
    private String collectorContact;
    private String currency;
    private boolean areMailsSent;
}
