package com.tamanna.interview_calendar.repository;

import jakarta.annotation.Nullable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
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
@Table(name = "participant_availability")
public class ParticipantAvailabilityModel {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nullable
    @ElementCollection
    @Column(length = 1000000000)
    private List<AvailabilityModel> availabilities;

    @OneToOne
    @JoinColumn(name = "participant_name", referencedColumnName = "name")
    private InterviewParticipantModel interviewParticipant;
}
