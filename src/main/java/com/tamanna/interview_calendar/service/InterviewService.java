package com.tamanna.interview_calendar.service;

import com.tamanna.interview_calendar.domain.Availability;
import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.ParticipantAvailability;
import com.tamanna.interview_calendar.domain.TimeSlot;
import com.tamanna.interview_calendar.service.component.InterviewServicePersistenceComponent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    private final InterviewServicePersistenceComponent interviewServicePersistenceComponent;

    public InterviewService(InterviewServicePersistenceComponent interviewServicePersistenceComponent) {
        this.interviewServicePersistenceComponent = interviewServicePersistenceComponent;
    }

    @Nonnull
    public InterviewParticipant createParticipant(@Nonnull InterviewParticipant interviewParticipant) {
        isAvailabilityValid(interviewParticipant);

        return interviewServicePersistenceComponent.save(interviewParticipant);
    }

    @Nonnull
    public List<InterviewParticipant> getAllInterviewParticipants() {

        return interviewServicePersistenceComponent.getAllInterviewParticipants();
    }

    @Nullable
    public InterviewParticipant getInterviewParticipant(@Nonnull String name) {

        return interviewServicePersistenceComponent.getInterviewParticipant(name);
    }

    private void isAvailabilityValid(@Nonnull InterviewParticipant interviewParticipant) {

        ParticipantAvailability participantAvailability = interviewParticipant.getParticipantAvailability();
        List<Availability> availabilities = participantAvailability.getAvailabilities();

        for (Availability availability : availabilities) {
            List<TimeSlot> timeSlots = availability.getTimeSlots();

            for (TimeSlot timeSlot : timeSlots) {
                timeSlot.isTimeSlotValid();
            }
        }
    }
}
