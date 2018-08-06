package com.dto;

public class UserIncludeDto extends UserDto {
    private boolean include;

    public UserIncludeDto(Long id, String preferredUsername, String firstName,
                          String lastName, String about, Integer children, boolean include, Boolean isVirtual) {
        super(id, preferredUsername, firstName, lastName, about, children, isVirtual);
        this.include = include;
    }

    public UserIncludeDto() {
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }
}
