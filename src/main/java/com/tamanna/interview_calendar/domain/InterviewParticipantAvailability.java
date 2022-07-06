package com.tamanna.interview_calendar.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class InterviewParticipantAvailability {

    @Nullable
    @JsonProperty(value = "availabilities")
    private List<Availability> availabilities;

    @JsonCreator
    public InterviewParticipantAvailability(@JsonProperty(value = "availabilities") @Nullable List<Availability> availabilities) {
        this.availabilities = availabilities;
    }
}
