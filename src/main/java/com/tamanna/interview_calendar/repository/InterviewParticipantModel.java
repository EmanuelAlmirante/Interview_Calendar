package com.tamanna.interview_calendar.repository;

import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "participant")
public class InterviewParticipantModel {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private Role role;

    public InterviewParticipant toDomain() {
        return InterviewParticipant.builder()
                                   .name(this.name)
                                   .role(this.role)
                                   .build();
    }
}
