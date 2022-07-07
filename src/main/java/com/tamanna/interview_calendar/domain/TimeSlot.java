package com.tamanna.interview_calendar.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tamanna.interview_calendar.exception.BusinessException;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode
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

    public boolean overlaps(TimeSlot other) {
        return (other.from.equals(this.from)
            && other.to.equals(this.to))
            || isBetween(other.from, this.from, this.to)
            || isBetween(other.to, this.from, this.to)
            || isBetween(this.from, other.from, other.to)
            || isBetween(this.to, other.from, other.to);
    }

    private boolean isBetween(LocalTime time, LocalTime from, LocalTime to) {
        if (from.isBefore(to)) {
            return from.isBefore(time) && time.isBefore(to);
        } else {
            return from.isBefore(time) || time.isBefore(to);
        }
    }
}
