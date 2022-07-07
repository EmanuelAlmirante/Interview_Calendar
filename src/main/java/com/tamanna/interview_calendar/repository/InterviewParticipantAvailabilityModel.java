package com.tamanna.interview_calendar.repository;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewParticipantAvailabilityModel implements Serializable {

    @Nullable
    private List<AvailabilityModel> availabilities;
}
