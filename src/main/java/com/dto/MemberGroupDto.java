package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberGroupDto {
    private String groupName;
    private Boolean requestAccepted;
}
