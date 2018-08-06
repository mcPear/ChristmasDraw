package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String preferredUsername;
    private String firstName;
    private String lastName;
    private String about;
    private Integer children;
    private Boolean isVirtual;
}
