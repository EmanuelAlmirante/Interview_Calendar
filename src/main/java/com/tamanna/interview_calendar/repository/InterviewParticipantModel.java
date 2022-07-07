package com.tamanna.interview_calendar.repository;

import com.tamanna.interview_calendar.common.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participant")
public class InterviewParticipantModel implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private Role role;

    @Column(name = "interview_participant_availability", length = 1000000000)
    private InterviewParticipantAvailabilityModel interviewParticipantAvailability;
}
