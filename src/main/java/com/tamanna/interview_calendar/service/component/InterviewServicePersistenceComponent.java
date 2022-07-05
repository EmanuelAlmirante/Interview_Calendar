package com.tamanna.interview_calendar.service.component;

import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.ParticipantAvailability;
import com.tamanna.interview_calendar.exception.BusinessException;
import com.tamanna.interview_calendar.repository.InterviewParticipantModel;
import com.tamanna.interview_calendar.repository.ParticipantAvailabilityModel;
import com.tamanna.interview_calendar.repository.interview.InterviewRepository;
import com.tamanna.interview_calendar.repository.participantavailability.ParticipantAvailabilityRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InterviewServicePersistenceComponent {

    @Nonnull
    private final InterviewRepository interviewRepository;

    @Nonnull
    private final ParticipantAvailabilityRepository participantAvailabilityRepository;

    @Nonnull
    private final ModelMapper mapper;

    public InterviewServicePersistenceComponent(InterviewRepository interviewRepository, ParticipantAvailabilityRepository participantAvailabilityRepository,
                                                ModelMapper mapper) {
        this.interviewRepository = interviewRepository;
        this.participantAvailabilityRepository = participantAvailabilityRepository;
        this.mapper = mapper;
    }

    @Nonnull
    public InterviewParticipant save(@Nonnull InterviewParticipant interviewParticipant) {
        InterviewParticipantModel interviewParticipantModel = mapper.map(interviewParticipant, InterviewParticipantModel.class);
        ParticipantAvailability participantAvailability = interviewParticipant.getParticipantAvailability();
        ParticipantAvailabilityModel participantAvailabilityModel = mapper.map(participantAvailability, ParticipantAvailabilityModel.class);

        participantAvailabilityModel.setInterviewParticipant(interviewParticipantModel);
        interviewParticipantModel.setParticipantAvailability(participantAvailabilityModel);

        try {
            interviewRepository.save(interviewParticipantModel);
            return interviewParticipant;
        } catch (Exception exception) {
            throw new BusinessException("Participant with name " + interviewParticipant.getName() + " already exists.");
        }
    }

    @Nonnull
    public List<InterviewParticipant> getAllInterviewParticipants() {
        List<InterviewParticipantModel> interviewParticipants = interviewRepository.findAll();
        List<InterviewParticipant> domainInterviewParticipants = new ArrayList<>();

        for (InterviewParticipantModel interviewParticipant : interviewParticipants) {
            InterviewParticipant domainInterviewParticipant = mapper.map(interviewParticipant, InterviewParticipant.class);
            domainInterviewParticipants.add(domainInterviewParticipant);
        }

        return domainInterviewParticipants;
    }

    @Nullable
    public InterviewParticipant getInterviewParticipant(@Nonnull String name) {

        InterviewParticipantModel interviewParticipant = interviewRepository.findById(name).orElse(null);

        if (interviewParticipant != null) {
            return mapper.map(interviewParticipant, InterviewParticipant.class);
        }

        return null;
    }
}
