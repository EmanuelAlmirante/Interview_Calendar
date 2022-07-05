package com.tamanna.interview_calendar.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tamanna.interview_calendar.common.Role;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class InterviewParticipant {

    @Nonnull
    @JsonProperty(value = "name")
    private String name;

    @Nonnull
    @JsonProperty(value = "role")
    private Role role;

    @Nullable
    @JsonProperty(value = "participant_availability")
    private ParticipantAvailability participantAvailability;

    @JsonCreator
    public InterviewParticipant(@JsonProperty(value = "name") @Nonnull String name,
                                @JsonProperty(value = "role") @Nonnull Role role,
                                @JsonProperty(value = "participant_availability") @Nullable ParticipantAvailability participantAvailability) {
        this.name = name;
        this.role = role;
        this.participantAvailability = participantAvailability;
    }
}
