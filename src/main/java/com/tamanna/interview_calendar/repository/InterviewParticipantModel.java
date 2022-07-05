package com.tamanna.interview_calendar.repository;

import com.tamanna.interview_calendar.common.Role;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(mappedBy = "interviewParticipant", orphanRemoval = true, cascade = CascadeType.ALL)
    private ParticipantAvailabilityModel participantAvailability;
}
