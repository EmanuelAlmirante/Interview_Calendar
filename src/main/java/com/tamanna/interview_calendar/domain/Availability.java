package com.tamanna.interview_calendar.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    public Availability mergeByDay(Availability availability) {
        this.timeSlots.addAll(availability.timeSlots);

        return this;
    }

    @JsonCreator
    public Availability(@JsonProperty(value = "day") @Nullable LocalDate day,
                        @JsonProperty(value = "timeslots") @Nullable List<TimeSlot> timeSlots) {
        this.day = day;
        this.timeSlots = timeSlots;
    }
}
