package com.tamanna.interview_calendar.service.component;

import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.exception.BusinessException;
import com.tamanna.interview_calendar.repository.InterviewParticipantModel;
import com.tamanna.interview_calendar.repository.InterviewRepository;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class InterviewServicePersistenceComponent {

    private final InterviewRepository interviewRepository;

    public InterviewServicePersistenceComponent(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public InterviewParticipant save(@Nonnull InterviewParticipant interviewParticipant) {
        InterviewParticipantModel interviewParticipantModel = new InterviewParticipantModel();
        interviewParticipantModel.setName(interviewParticipant.getName());
        interviewParticipantModel.setRole(interviewParticipant.getRole());

        try {
            interviewRepository.save(interviewParticipantModel);
            return interviewParticipant;
        } catch (Exception exception) {
            throw new BusinessException("Participant with name " + interviewParticipant.getName() + " already exists.");
        }
    }
}
