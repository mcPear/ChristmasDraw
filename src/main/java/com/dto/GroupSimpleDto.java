package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GroupSimpleDto {
    private Long id;
    private String name;
    private Timestamp drawDate;
}
