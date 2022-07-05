package com.tamanna.interview_calendar.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    CANDIDATE("candidate"),
    INTERVIEWER("interviewer");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @JsonCreator
    public static Role fromString(String role) {
        return role == null ? null : Role.valueOf(role.toUpperCase());
    }

    @JsonValue
    public String getRole() {
        return role;
    }
}
