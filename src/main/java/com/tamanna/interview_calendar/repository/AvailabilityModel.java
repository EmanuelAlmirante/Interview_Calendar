package com.tamanna.interview_calendar.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDate;
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
public class AvailabilityModel implements Serializable {

    @JsonProperty(value = "day")
    private LocalDate day;

    @JsonProperty(value = "timeSlots")
    private List<TimeSlotModel> timeSlots;
}
