package com.tamanna.interview_calendar.service;

import com.tamanna.interview_calendar.domain.Availability;
import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.ParticipantAvailability;
import com.tamanna.interview_calendar.domain.TimeSlot;
import com.tamanna.interview_calendar.exception.BusinessException;
import com.tamanna.interview_calendar.repository.AvailabilityModel;
import com.tamanna.interview_calendar.repository.InterviewParticipantModel;
import com.tamanna.interview_calendar.repository.ParticipantAvailabilityModel;
import com.tamanna.interview_calendar.repository.TimeSlotModel;
import com.tamanna.interview_calendar.repository.interview.InterviewRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    @Nonnull
    private final InterviewRepository interviewRepository;

    @Nonnull
    private final ModelMapper mapper;

    public InterviewService(InterviewRepository interviewRepository, ModelMapper mapper) {
        this.interviewRepository = interviewRepository;
        this.mapper = mapper;
    }

    @Nonnull
    public InterviewParticipant createParticipant(@Nonnull InterviewParticipant interviewParticipant) {

        isAvailabilityValid(interviewParticipant);

        InterviewParticipantModel interviewParticipantModel = mapper.map(interviewParticipant, InterviewParticipantModel.class);
        ParticipantAvailability participantAvailability = interviewParticipant.getParticipantAvailability();

        if (participantAvailability != null) {
            ParticipantAvailabilityModel participantAvailabilityModel = mapper.map(participantAvailability, ParticipantAvailabilityModel.class);
            participantAvailabilityModel.setInterviewParticipant(interviewParticipantModel);
            interviewParticipantModel.setParticipantAvailability(participantAvailabilityModel);
        }

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

        throw new BusinessException("No participant with name " + name + " found.");
    }

    public void deleteInterviewParticipant(@Nonnull String name) {

        interviewRepository.deleteById(name);
    }

    @Nullable
    public ParticipantAvailability getInterviewParticipantAvailability(@Nonnull String name) {

        InterviewParticipantModel interviewParticipant = interviewRepository.findById(name).orElse(null);

        if (interviewParticipant != null) {
            ParticipantAvailabilityModel participantAvailability = interviewParticipant.getParticipantAvailability();

            return participantAvailability == null ? new ParticipantAvailability()
                                                   : mapper.map(participantAvailability, ParticipantAvailability.class);
        }

        throw new BusinessException("No participant with name " + name + " found.");
    }

    @Nonnull
    public ParticipantAvailability updateInterviewParticipantAvailability(@Nonnull String name, @Nonnull Availability availability) {

        InterviewParticipantModel interviewParticipant = interviewRepository.findById(name).orElse(null);

        if (interviewParticipant != null) {
            ParticipantAvailabilityModel participantAvailability = interviewParticipant.getParticipantAvailability() == null ?
                                                                   null : interviewParticipant.getParticipantAvailability();

            if (participantAvailability != null) {
                AvailabilityModel toBeAddedAvailability = mapper.map(availability, AvailabilityModel.class);
                LocalDate toBeAddedDay = toBeAddedAvailability.getDay();
                List<AvailabilityModel> existingAvailabilities = participantAvailability.getAvailabilities();
                Optional<AvailabilityModel> existingAvailability = existingAvailabilities.stream()
                                                                                         .filter(availabilityModel -> availabilityModel.getDay()
                                                                                                                                       .equals(
                                                                                                                                           toBeAddedDay))
                                                                                         .findFirst();

                existingAvailability.ifPresent(existingAvailabilities::remove);
                existingAvailabilities.add(toBeAddedAvailability);
                participantAvailability.setAvailabilities(existingAvailabilities);
            } else {
                participantAvailability = new ParticipantAvailabilityModel();
                AvailabilityModel toBeAddedAvailability = mapper.map(availability, AvailabilityModel.class);
                participantAvailability.setAvailabilities(List.of(toBeAddedAvailability));
            }

            interviewParticipant.setParticipantAvailability(participantAvailability);
            interviewRepository.save(interviewParticipant);

            return mapper.map(participantAvailability, ParticipantAvailability.class);
        }

        throw new BusinessException("No participant with name " + name + " found.");
    }

    public void deleteInterviewParticipantAvailability(@Nonnull String name, @Nonnull Availability availability) {

        InterviewParticipantModel interviewParticipant = interviewRepository.findById(name).orElse(null);

        if (interviewParticipant != null) {
            ParticipantAvailabilityModel existingParticipantAvailability = interviewParticipant.getParticipantAvailability() == null ?
                                                                           null : interviewParticipant.getParticipantAvailability();

            if (existingParticipantAvailability != null) {
                List<AvailabilityModel> existingAvailabilities = existingParticipantAvailability.getAvailabilities();
                AvailabilityModel toBeDeletedAvailability = mapper.map(availability, AvailabilityModel.class);

                for (AvailabilityModel existingAvailability : existingAvailabilities) {
                    LocalDate existingDay = existingAvailability.getDay();
                    LocalDate toBeDeletedDay = toBeDeletedAvailability.getDay();

                    if (existingDay.equals(toBeDeletedDay)) {
                        List<TimeSlotModel> existingTimeSlots = existingAvailability.getTimeSlots();
                        List<TimeSlotModel> toBeDeletedTimeSlots = toBeDeletedAvailability.getTimeSlots();

                        existingTimeSlots.removeAll(toBeDeletedTimeSlots);
                    }
                }

                existingAvailabilities = existingAvailabilities.stream().filter(availabilityModel -> !availabilityModel.getTimeSlots().isEmpty())
                                                               .collect(Collectors.toList());
                existingParticipantAvailability.setAvailabilities(existingAvailabilities);
                interviewParticipant.setParticipantAvailability(existingParticipantAvailability);

                interviewRepository.save(interviewParticipant);
            }
        }
    }

    private void isAvailabilityValid(@Nonnull InterviewParticipant interviewParticipant) {

        ParticipantAvailability participantAvailability = interviewParticipant.getParticipantAvailability();

        if (participantAvailability != null) {
            List<Availability> availabilities = participantAvailability.getAvailabilities();

            for (Availability availability : availabilities) {
                List<TimeSlot> timeSlots = availability.getTimeSlots();

                for (TimeSlot timeSlot : timeSlots) {
                    timeSlot.isTimeSlotValid();
                }
            }
        }
    }
}
