package com.tamanna.interview_calendar.domain.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tamanna.interview_calendar.domain.Availability;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CommonTimeSlotsResponse {

    @Nonnull
    @JsonProperty(value = "candidate_name")
    private String candidateName;

    @Nonnull
    @JsonProperty(value = "interviewers_names")
    private List<String> interviewersNames;

    @Nullable
    @JsonProperty(value = "availabilities")
    private List<Availability> availabilities;

    @JsonCreator
    public CommonTimeSlotsResponse(@JsonProperty(value = "candidate_name") @Nonnull String candidateName,
                                   @JsonProperty(value = "interviewers_names") @Nonnull List<String> interviewersNames,
                                   @JsonProperty(value = "availabilities") @Nullable List<Availability> availabilities) {
        this.candidateName = candidateName;
        this.interviewersNames = interviewersNames;
        this.availabilities = availabilities;
    }
}
