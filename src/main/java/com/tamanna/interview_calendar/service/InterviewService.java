package com.tamanna.interview_calendar.service;

import com.tamanna.interview_calendar.common.Role;
import com.tamanna.interview_calendar.domain.Availability;
import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.InterviewParticipantAvailability;
import com.tamanna.interview_calendar.domain.TimeSlot;
import com.tamanna.interview_calendar.domain.response.CommonTimeSlotsResponse;
import com.tamanna.interview_calendar.exception.BusinessException;
import com.tamanna.interview_calendar.repository.AvailabilityModel;
import com.tamanna.interview_calendar.repository.InterviewParticipantAvailabilityModel;
import com.tamanna.interview_calendar.repository.InterviewParticipantModel;
import com.tamanna.interview_calendar.repository.InterviewRepository;
import com.tamanna.interview_calendar.repository.TimeSlotModel;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        verifyIfParticipantAlreadyExists(interviewParticipant);
        sortAvailabilitiesByFrom(interviewParticipant);

        InterviewParticipantModel interviewParticipantModel = mapper.map(interviewParticipant, InterviewParticipantModel.class);
        interviewRepository.save(interviewParticipantModel);

        return interviewParticipant;
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
    public InterviewParticipantAvailability getInterviewParticipantAvailability(@Nonnull String name) {

        InterviewParticipantModel interviewParticipant = interviewRepository.findById(name).orElse(null);

        if (interviewParticipant != null) {
            InterviewParticipantAvailabilityModel participantAvailability = interviewParticipant.getInterviewParticipantAvailability();

            return participantAvailability == null ? new InterviewParticipantAvailability()
                                                   : mapper.map(participantAvailability, InterviewParticipantAvailability.class);
        }

        throw new BusinessException("No participant with name " + name + " found.");
    }

    @Nonnull
    public InterviewParticipantAvailability updateInterviewParticipantAvailability(@Nonnull String name, @Nonnull Availability availability) {

        InterviewParticipantModel interviewParticipant = interviewRepository.findById(name).orElse(null);

        if (interviewParticipant != null) {
            InterviewParticipantAvailabilityModel participantAvailability = interviewParticipant.getInterviewParticipantAvailability() == null ?
                                                                            null : interviewParticipant.getInterviewParticipantAvailability();

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
                participantAvailability = new InterviewParticipantAvailabilityModel();
                AvailabilityModel toBeAddedAvailability = mapper.map(availability, AvailabilityModel.class);
                participantAvailability.setAvailabilities(List.of(toBeAddedAvailability));
            }

            isAvailabilityValid(mapper.map(participantAvailability, InterviewParticipant.class));
            interviewParticipant.setInterviewParticipantAvailability(participantAvailability);
            interviewRepository.save(interviewParticipant);

            return mapper.map(participantAvailability, InterviewParticipantAvailability.class);
        }

        throw new BusinessException("No participant with name " + name + " found.");
    }

    public void deleteInterviewParticipantAvailability(@Nonnull String name, @Nonnull Availability availability) {

        InterviewParticipantModel interviewParticipant = interviewRepository.findById(name).orElse(null);

        if (interviewParticipant != null) {
            InterviewParticipantAvailabilityModel existingParticipantAvailability =
                interviewParticipant.getInterviewParticipantAvailability() == null ?
                null : interviewParticipant.getInterviewParticipantAvailability();

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
                interviewParticipant.setInterviewParticipantAvailability(existingParticipantAvailability);

                interviewRepository.save(interviewParticipant);
            }
        }
    }

    @Nullable
    public CommonTimeSlotsResponse getCommonTimeSlotsCandidateAndInterviewers(@Nonnull String candidateName,
                                                                              @Nonnull List<String> interviewersNames) {

        List<Availability> candidateAvailabilities = getCandidateAvailabilities(candidateName);
        List<Availability> interviewersAvailabilities = getInterviewersAvailabilities(interviewersNames);

        List<Availability> commonAvailabilities = new ArrayList<>();

        for (Availability candidateAvailability : candidateAvailabilities) {
            Set<TimeSlot> overlappingTimeSlots = new HashSet<>();
            LocalDate candidateAvailabilityDay = candidateAvailability.getDay();
            List<TimeSlot> candidateTimeSlots = candidateAvailability.getTimeSlots();

            for (Availability interviewersAvailability : interviewersAvailabilities) {
                LocalDate interviewerAvailabilityDay = interviewersAvailability.getDay();
                List<TimeSlot> interviewerTimeSlots = interviewersAvailability.getTimeSlots();

                if (candidateAvailabilityDay.equals(interviewerAvailabilityDay)) {
                    for (TimeSlot candidateTimeSlot : candidateTimeSlots) {
                        for (TimeSlot interviewerTimeSlot : interviewerTimeSlots) {
                            if (candidateTimeSlot.overlaps(interviewerTimeSlot)) {
                                TimeSlot overlappingTimeSlot = computeOverlappingTimeSlot(candidateTimeSlot, interviewerTimeSlot);

                                if (overlappingTimeSlot != null) {
                                    overlappingTimeSlots.add(overlappingTimeSlot);
                                }
                            }
                        }
                    }
                }
            }

            if (!overlappingTimeSlots.isEmpty()) {
                if (!commonAvailabilities.isEmpty()) {
                    for (Availability commonAvailability : commonAvailabilities) {
                        if (commonAvailability.getDay().equals(candidateAvailabilityDay)) {
                            commonAvailability.getTimeSlots().addAll(overlappingTimeSlots);
                        }
                    }
                } else {
                    Availability commonAvailability = new Availability(candidateAvailabilityDay, new ArrayList<>(overlappingTimeSlots));
                    commonAvailabilities.add(commonAvailability);
                }
            }
        }

        return new CommonTimeSlotsResponse(candidateName, interviewersNames, commonAvailabilities);
    }

    private void verifyIfParticipantAlreadyExists(@Nonnull InterviewParticipant interviewParticipant) {

        String name = interviewParticipant.getName();
        InterviewParticipantModel interviewParticipantModel = interviewRepository.findById(name).orElse(null);

        if (interviewParticipantModel != null) {
            throw new BusinessException("Participant with name " + interviewParticipant.getName() + " already exists.");
        }
    }

    private void sortAvailabilitiesByFrom(@Nonnull InterviewParticipant interviewParticipant) {

        List<Availability> availabilities = interviewParticipant.getInterviewParticipantAvailability().getAvailabilities();

        for (Availability availability : availabilities) {
            List<TimeSlot> timeSlots = availability.getTimeSlots();

            timeSlots.sort(Comparator.comparing(TimeSlot::getFrom));
        }
    }

    private void isAvailabilityValid(@Nonnull InterviewParticipant interviewParticipant) {

        InterviewParticipantAvailability interviewParticipantAvailability = interviewParticipant.getInterviewParticipantAvailability();

        if (interviewParticipantAvailability != null) {
            List<Availability> availabilities = interviewParticipantAvailability.getAvailabilities();

            for (Availability availability : availabilities) {
                List<TimeSlot> timeSlots = availability.getTimeSlots();

                for (TimeSlot timeSlot : timeSlots) {
                    timeSlot.isTimeSlotValid();
                }
            }
        }
    }

    private void isCandidateValid(@Nonnull String candidateName, @Nullable InterviewParticipantModel candidate) {

        if (candidate == null) {
            throw new BusinessException("No candidate with name " + candidateName + " found.");
        }

        if (candidate.getRole() != Role.CANDIDATE) {
            throw new BusinessException("Participant with name " + candidateName + " does not have a candidate role.");
        }
    }

    @Nonnull
    private List<Availability> getCandidateAvailabilities(@Nonnull String candidateName) {

        InterviewParticipantModel candidate = interviewRepository.findById(candidateName).orElse(null);
        isCandidateValid(candidateName, candidate);
        InterviewParticipant domainCandidate = mapper.map(candidate, InterviewParticipant.class);

        return domainCandidate.getInterviewParticipantAvailability().getAvailabilities();
    }

    @Nonnull
    private List<Availability> getInterviewersAvailabilities(@Nonnull List<String> interviewersNames) {

        List<Availability> interviewersAvailabilities = new ArrayList<>();

        for (String interviewerName : interviewersNames) {
            InterviewParticipantModel interviewer = interviewRepository.findById(interviewerName).orElse(null);
            isInterviewerValid(interviewerName, interviewer);

            InterviewParticipant domainInterviewer = mapper.map(interviewer, InterviewParticipant.class);
            List<Availability> interviewerAvailabilities = domainInterviewer.getInterviewParticipantAvailability().getAvailabilities();
            interviewersAvailabilities.addAll(interviewerAvailabilities);
        }

        return interviewersAvailabilities;
    }

    private void isInterviewerValid(@Nonnull String interviewerName, @Nullable InterviewParticipantModel interviewer) {

        if (interviewer == null) {
            throw new BusinessException("No interviewer with name " + interviewerName + " found.");
        }

        if (interviewer.getRole() != Role.INTERVIEWER) {
            throw new BusinessException("Participant with name " + interviewerName + " does not have an interviewer role.");
        }
    }

    @Nonnull
    private TimeSlot computeOverlappingTimeSlot(@Nonnull TimeSlot candidateTimeSlot, @Nonnull TimeSlot interviewerTimeSlot) {

        TimeSlot overlappingTimeSlot = null;
        LocalTime overlappingFrom;
        LocalTime overlappingTo;

        LocalTime candidateFrom = candidateTimeSlot.getFrom();
        LocalTime candidateTo = candidateTimeSlot.getTo();
        LocalTime interviewerFrom = interviewerTimeSlot.getFrom();
        LocalTime interviewerTo = interviewerTimeSlot.getTo();

        if (candidateFrom.isBefore(interviewerTo) && interviewerFrom.isBefore(candidateTo)) {
            overlappingTimeSlot = new TimeSlot();

            if (candidateFrom.isBefore(interviewerFrom)) {
                overlappingFrom = interviewerFrom;
            } else {
                overlappingFrom = candidateFrom;
            }

            if (candidateTo.isBefore(interviewerTo)) {
                overlappingTo = candidateTo;
            } else {
                overlappingTo = interviewerTo;
            }

            overlappingTimeSlot.setFrom(overlappingFrom);
            overlappingTimeSlot.setTo(overlappingTo);
        }

        return overlappingTimeSlot;
    }
}
