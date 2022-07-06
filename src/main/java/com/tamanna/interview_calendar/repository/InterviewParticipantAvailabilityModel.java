package com.tamanna.interview_calendar.repository;

import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewParticipantAvailabilityModel implements Serializable {

    @Nullable
    private List<AvailabilityModel> availabilities;
}
