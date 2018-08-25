package com.dto;

public class UserIncludeDto extends UserDto {
    private boolean includeInFutureDraw;
    private Boolean includedInLastDraw;

    public UserIncludeDto(Long id, String preferredUsername, String firstName, String lastName, String email,
                          String about, Integer children, boolean includeInFutureDraw, Boolean includedInLastDraw,
                          Boolean isVirtual, String lang) {
        super(id, preferredUsername, firstName, lastName, email, about, children, isVirtual, lang);
        this.includeInFutureDraw = includeInFutureDraw;
        this.includedInLastDraw = includedInLastDraw;
    }

    public boolean isIncludeInFutureDraw() {
        return includeInFutureDraw;
    }

    public void setIncludeInFutureDraw(boolean includeInFutureDraw) {
        this.includeInFutureDraw = includeInFutureDraw;
    }

    public Boolean getIncludedInLastDraw() {
        return includedInLastDraw;
    }

    public void setIncludedInLastDraw(Boolean includedInLastDraw) {
        this.includedInLastDraw = includedInLastDraw;
    }
}
