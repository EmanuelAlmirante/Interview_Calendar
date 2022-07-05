package com.tamanna.interview_calendar.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tamanna.interview_calendar.exception.BusinessException;
import jakarta.annotation.Nullable;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class TimeSlot {

    @Nullable
    @JsonProperty(value = "from")
    private LocalTime from;

    @Nullable
    @JsonProperty(value = "to")
    private LocalTime to;

    @JsonCreator
    public TimeSlot(@JsonProperty(value = "from") @Nullable LocalTime from,
                    @JsonProperty(value = "to") @Nullable LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public void isTimeSlotValid() {

        if (from.isAfter(to) || from.equals(to)) {
            throw new BusinessException("Start hour must be before end hour.", "From: " + from, "To: " + to);
        }

        if (from.getMinute() != 0 || to.getMinute() != 0) {
            throw new BusinessException("Time slots must be from the beginning of the hour until the beginning of the next hour.",
                                        "From: " + from, "To: " + to);
        }
    }
}
