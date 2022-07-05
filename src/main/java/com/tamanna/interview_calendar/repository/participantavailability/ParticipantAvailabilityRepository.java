package com.tamanna.interview_calendar.repository.participantavailability;

import com.tamanna.interview_calendar.repository.ParticipantAvailabilityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantAvailabilityRepository extends JpaRepository<ParticipantAvailabilityModel, Long> {
}
