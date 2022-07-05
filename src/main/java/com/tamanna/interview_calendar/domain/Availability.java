package com.tamanna.interview_calendar.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Availability {

    @Nullable
    @JsonProperty(value = "day")
    private LocalDate day;

    @Nullable
    @JsonProperty(value = "timeslots")
    private List<TimeSlot> timeSlots;

    @JsonCreator
    public Availability(@JsonProperty(value = "day") @Nullable LocalDate day,
                        @JsonProperty(value = "timeslots") @Nullable List<TimeSlot> timeSlots) {
        this.day = day;
        this.timeSlots = timeSlots;
    }
}
