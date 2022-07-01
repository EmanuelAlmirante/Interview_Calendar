package com.tamanna.interview_calendar.service;

import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.service.component.InterviewServicePersistenceComponent;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    private final InterviewServicePersistenceComponent interviewServicePersistenceComponent;

    public InterviewService(InterviewServicePersistenceComponent interviewServicePersistenceComponent) {
        this.interviewServicePersistenceComponent = interviewServicePersistenceComponent;
    }

    @Nonnull
    public InterviewParticipant createParticipant(@Nonnull InterviewParticipant interviewParticipant) {

        return interviewServicePersistenceComponent.save(interviewParticipant);
    }
}
