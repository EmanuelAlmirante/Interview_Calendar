package com.tamanna.interview_calendar.repository;

import com.tamanna.interview_calendar.repository.InterviewParticipantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<InterviewParticipantModel, String> {
}
