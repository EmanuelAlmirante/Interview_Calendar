package com.tamanna.interview_calendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class InterviewParticipant {

    @Nonnull
    @JsonProperty(value = "name")
    private String name;

    @Nonnull
    @JsonProperty(value = "role")
    private Role role;
}
