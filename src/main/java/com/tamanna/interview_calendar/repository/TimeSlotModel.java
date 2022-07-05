package com.tamanna.interview_calendar.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalTime;
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
public class TimeSlotModel implements Serializable {

    @JsonProperty(value = "from")
    private LocalTime from;

    @JsonProperty(value = "to")
    private LocalTime to;
}
