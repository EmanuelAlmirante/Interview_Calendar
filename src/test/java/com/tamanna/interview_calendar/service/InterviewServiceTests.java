package com.tamanna.interview_calendar.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;

import com.tamanna.interview_calendar.common.Role;
import com.tamanna.interview_calendar.domain.Availability;
import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.InterviewParticipantAvailability;
import com.tamanna.interview_calendar.domain.TimeSlot;
import com.tamanna.interview_calendar.domain.response.CommonTimeSlotsResponse;
import com.tamanna.interview_calendar.exception.BusinessException;
import com.tamanna.interview_calendar.repository.InterviewParticipantModel;
import com.tamanna.interview_calendar.repository.InterviewRepository;
import com.tamanna.interview_calendar.service.utils.InterviewServiceTestsUtils;

import net.bytebuddy.asm.Advice.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTests {

    @Mock
    private InterviewRepository interviewRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private InterviewService interviewService;

    @Test
    public void create_participant_with_role_candidate_without_availability_successfully() {
        // Arrange
        String name = "Test";
        Role role = Role.CANDIDATE;
        InterviewParticipant interviewParticipant = InterviewServiceTestsUtils.createInterviewParticipant(name, role, null);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.save(any(InterviewParticipantModel.class))).thenReturn(interviewParticipantModel);

        InterviewParticipant savedInterviewParticipant = interviewService.createParticipant(interviewParticipant);

        // Assert
        assertNotNull(savedInterviewParticipant);
        assertEquals(name, savedInterviewParticipant.getName());
        assertEquals(role, savedInterviewParticipant.getRole());
        assertNull(savedInterviewParticipant.getInterviewParticipantAvailability());
    }

    @Test
    public void create_participant_with_interviewer_candidate_without_availability_successfully() {
        // Arrange
        String name = "Test";
        Role role = Role.INTERVIEWER;
        InterviewParticipant interviewParticipant = InterviewServiceTestsUtils.createInterviewParticipant(name, role, null);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.save(any(InterviewParticipantModel.class))).thenReturn(interviewParticipantModel);

        InterviewParticipant savedInterviewParticipant = interviewService.createParticipant(interviewParticipant);

        // Assert
        assertNotNull(savedInterviewParticipant);
        assertEquals(name, savedInterviewParticipant.getName());
        assertEquals(role, savedInterviewParticipant.getRole());
        assertNull(savedInterviewParticipant.getInterviewParticipantAvailability());
    }

    @Test
    public void create_participant_with_role_candidate_with_availability_successfully() {
        // Arrange
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(10, 0);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String name = "Test";
        Role role = Role.CANDIDATE;
        InterviewParticipant interviewParticipant = new InterviewParticipant(name, role, interviewParticipantAvailability);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.save(any(InterviewParticipantModel.class))).thenReturn(interviewParticipantModel);

        InterviewParticipant savedInterviewParticipant = interviewService.createParticipant(interviewParticipant);

        // Assert
        assertNotNull(savedInterviewParticipant);
        assertEquals(name, savedInterviewParticipant.getName());
        assertEquals(role, savedInterviewParticipant.getRole());
        assertNotNull(savedInterviewParticipant.getInterviewParticipantAvailability());
        assertEquals(interviewParticipantAvailability, savedInterviewParticipant.getInterviewParticipantAvailability());
    }

    @Test
    public void create_participant_with_role_interviewer_with_availability_successfully() {
        // Arrange
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(10, 0);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String name = "Test";
        Role role = Role.INTERVIEWER;
        InterviewParticipant interviewParticipant = new InterviewParticipant(name, role, interviewParticipantAvailability);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.save(any(InterviewParticipantModel.class))).thenReturn(interviewParticipantModel);

        InterviewParticipant savedInterviewParticipant = interviewService.createParticipant(interviewParticipant);

        // Assert
        assertNotNull(savedInterviewParticipant);
        assertEquals(name, savedInterviewParticipant.getName());
        assertEquals(role, savedInterviewParticipant.getRole());
        assertNotNull(savedInterviewParticipant.getInterviewParticipantAvailability());
        assertEquals(interviewParticipantAvailability, savedInterviewParticipant.getInterviewParticipantAvailability());
    }

    @Test
    public void create_participant_with_from_after_to_time_slot_fails() {
        // Arrange
        LocalTime from = LocalTime.of(10, 0);
        LocalTime to = LocalTime.of(9, 0);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String name = "Test";
        Role role = Role.CANDIDATE;
        InterviewParticipant interviewParticipant = new InterviewParticipant(name, role, interviewParticipantAvailability);

        // Act && Assert
        BusinessException businessException = assertThrows(BusinessException.class, () -> interviewService.createParticipant(interviewParticipant));
        assertEquals("Start hour must be before end hour.", businessException.getMessage());
        assertEquals("From: " + from, businessException.getArguments()[0]);
        assertEquals("To: " + to, businessException.getArguments()[1]);
    }

    @Test
    public void create_participant_with_from_and_to_spanning_half_hours_time_slot_fails() {
        // Arrange
        LocalTime from = LocalTime.of(9, 30);
        LocalTime to = LocalTime.of(10, 30);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String name = "Test";
        Role role = Role.CANDIDATE;
        InterviewParticipant interviewParticipant = new InterviewParticipant(name, role, interviewParticipantAvailability);

        // Act && Assert
        BusinessException businessException = assertThrows(BusinessException.class, () -> interviewService.createParticipant(interviewParticipant));
        assertEquals("Time slots must be from the beginning of the hour until the beginning of the next hour.", businessException.getMessage());
        assertEquals("From: " + from, businessException.getArguments()[0]);
        assertEquals("To: " + to, businessException.getArguments()[1]);
    }

    @Test
    public void create_participant_with_already_existing_name_fails() {
        // Arrange
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(10, 0);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String name = "Test";
        Role role = Role.CANDIDATE;
        InterviewParticipant interviewParticipant = new InterviewParticipant(name, role, interviewParticipantAvailability);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        // Act && Assert
        when(interviewRepository.findById(name)).thenReturn(Optional.ofNullable(interviewParticipantModel));

        BusinessException businessException = assertThrows(BusinessException.class, () -> interviewService.createParticipant(interviewParticipant));
        assertEquals("Participant with name " + interviewParticipant.getName() + " already exists.", businessException.getMessage());
    }

    @Test
    public void candidate_and_interviewer_have_no_common_time_slots_successfully() {
        // Arrange
        LocalTime candidateFrom = LocalTime.of(9, 0);
        LocalTime candidateTo = LocalTime.of(10, 0);
        TimeSlot candidateTimeSlot = InterviewServiceTestsUtils.createTimeSlot(candidateFrom, candidateTo);

        LocalDate candidateDay = LocalDate.of(2022, 12, 31);
        Availability candidateAvailability = InterviewServiceTestsUtils.createAvailability(candidateDay, Arrays.asList(candidateTimeSlot));

        InterviewParticipantAvailability candidateInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(candidateAvailability));

        String candidateName = "Candidate";
        Role candidateRole = Role.CANDIDATE;
        InterviewParticipant candidate = new InterviewParticipant(candidateName, candidateRole, candidateInterviewParticipantAvailability);

        InterviewParticipantModel candidateModel = modelMapper.map(candidate, InterviewParticipantModel.class);

        LocalTime interviewerFrom = LocalTime.of(12, 0);
        LocalTime interviewerTo = LocalTime.of(13, 0);
        TimeSlot interviewerTimeSlot = InterviewServiceTestsUtils.createTimeSlot(interviewerFrom, interviewerTo);

        LocalDate interviewerDay = LocalDate.of(2022, 12, 31);
        Availability interviewerAvailability = InterviewServiceTestsUtils.createAvailability(interviewerDay, Arrays.asList(interviewerTimeSlot));

        InterviewParticipantAvailability interviewerInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(interviewerAvailability));

        String interviewerName = "Interviewer";
        Role interviewerRole = Role.INTERVIEWER;
        InterviewParticipant interviewer = new InterviewParticipant(interviewerName, interviewerRole, interviewerInterviewParticipantAvailability);

        InterviewParticipantModel interviewerModel = modelMapper.map(interviewer, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.ofNullable(candidateModel));
        when(interviewRepository.findById(interviewerName)).thenReturn(Optional.ofNullable(interviewerModel));

        CommonTimeSlotsResponse commonTimeSlotsResponse = interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName, Arrays.asList(interviewerName));

        // Assert
        assertNotNull(commonTimeSlotsResponse);
        assertEquals(candidateName, commonTimeSlotsResponse.getCandidateName());
        assertTrue(commonTimeSlotsResponse.getInterviewersNames().contains(interviewerName));
        assertTrue(commonTimeSlotsResponse.getAvailabilities().isEmpty());
    }

    @Test
    public void candidate_and_interviewer_have_one_common_time_slots_same_day_successfully() {
        // Arrange
        LocalTime candidateFrom = LocalTime.of(9, 0);
        LocalTime candidateTo = LocalTime.of(12, 0);
        TimeSlot candidateTimeSlot = InterviewServiceTestsUtils.createTimeSlot(candidateFrom, candidateTo);

        LocalDate candidateDay = LocalDate.of(2022, 12, 31);
        Availability candidateAvailability = InterviewServiceTestsUtils.createAvailability(candidateDay, Arrays.asList(candidateTimeSlot));

        InterviewParticipantAvailability candidateInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(candidateAvailability));

        String candidateName = "Candidate";
        Role candidateRole = Role.CANDIDATE;
        InterviewParticipant candidate = new InterviewParticipant(candidateName, candidateRole, candidateInterviewParticipantAvailability);

        InterviewParticipantModel candidateModel = modelMapper.map(candidate, InterviewParticipantModel.class);

        LocalTime interviewerFrom = LocalTime.of(10, 0);
        LocalTime interviewerTo = LocalTime.of(11, 0);
        TimeSlot interviewerTimeSlot = InterviewServiceTestsUtils.createTimeSlot(interviewerFrom, interviewerTo);

        LocalDate interviewerDay = LocalDate.of(2022, 12, 31);
        Availability interviewerAvailability = InterviewServiceTestsUtils.createAvailability(interviewerDay, Arrays.asList(interviewerTimeSlot));

        InterviewParticipantAvailability interviewerInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(interviewerAvailability));

        String interviewerName = "Interviewer";
        Role interviewerRole = Role.INTERVIEWER;
        InterviewParticipant interviewer = new InterviewParticipant(interviewerName, interviewerRole, interviewerInterviewParticipantAvailability);

        InterviewParticipantModel interviewerModel = modelMapper.map(interviewer, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.ofNullable(candidateModel));
        when(interviewRepository.findById(interviewerName)).thenReturn(Optional.ofNullable(interviewerModel));

        CommonTimeSlotsResponse commonTimeSlotsResponse = interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName, Arrays.asList(interviewerName));

        // Assert
        TimeSlot expectedTimeSlot = InterviewServiceTestsUtils.createTimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0));

        assertNotNull(commonTimeSlotsResponse);
        assertEquals(candidateName, commonTimeSlotsResponse.getCandidateName());
        assertTrue(commonTimeSlotsResponse.getInterviewersNames().contains(interviewerName));
        assertFalse(commonTimeSlotsResponse.getAvailabilities().isEmpty());
        assertThat(commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots(), hasItem(allOf(
            hasProperty("from", is(expectedTimeSlot.getFrom())),
            hasProperty("to", is(expectedTimeSlot.getTo()))
        )));
        assertEquals(expectedTimeSlot, commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots().get(0));
    }

    @Test
    public void candidate_and_interviewer_have_two_common_time_slots_same_day_successfully() {
        // Arrange
        LocalTime candidateFrom = LocalTime.of(9, 0);
        LocalTime candidateTo = LocalTime.of(18, 0);
        TimeSlot candidateTimeSlot = InterviewServiceTestsUtils.createTimeSlot(candidateFrom, candidateTo);

        LocalDate candidateDay = LocalDate.of(2022, 12, 31);
        Availability candidateAvailability = InterviewServiceTestsUtils.createAvailability(candidateDay, Arrays.asList(candidateTimeSlot));

        InterviewParticipantAvailability candidateInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(candidateAvailability));

        String candidateName = "Candidate";
        Role candidateRole = Role.CANDIDATE;
        InterviewParticipant candidate = new InterviewParticipant(candidateName, candidateRole, candidateInterviewParticipantAvailability);

        InterviewParticipantModel candidateModel = modelMapper.map(candidate, InterviewParticipantModel.class);

        LocalTime interviewerFromOne = LocalTime.of(10, 0);
        LocalTime interviewerToOne = LocalTime.of(11, 0);
        TimeSlot interviewerTimeSlotOne = InterviewServiceTestsUtils.createTimeSlot(interviewerFromOne, interviewerToOne);

        LocalTime interviewerFromTwo = LocalTime.of(14, 0);
        LocalTime interviewerToTwo = LocalTime.of(15, 0);
        TimeSlot interviewerTimeSlotTwo = InterviewServiceTestsUtils.createTimeSlot(interviewerFromTwo, interviewerToTwo);

        LocalDate interviewerDay = LocalDate.of(2022, 12, 31);
        Availability interviewerAvailability = InterviewServiceTestsUtils.createAvailability(interviewerDay, Arrays.asList(interviewerTimeSlotOne, interviewerTimeSlotTwo));

        InterviewParticipantAvailability interviewerInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(interviewerAvailability));

        String interviewerName = "Interviewer";
        Role interviewerRole = Role.INTERVIEWER;
        InterviewParticipant interviewer = new InterviewParticipant(interviewerName, interviewerRole, interviewerInterviewParticipantAvailability);

        InterviewParticipantModel interviewerModel = modelMapper.map(interviewer, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.ofNullable(candidateModel));
        when(interviewRepository.findById(interviewerName)).thenReturn(Optional.ofNullable(interviewerModel));

        CommonTimeSlotsResponse commonTimeSlotsResponse = interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName, Arrays.asList(interviewerName));

        // Assert
        TimeSlot expectedTimeSlotOne = InterviewServiceTestsUtils.createTimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0));
        TimeSlot expectedTimeSlotTw0 = InterviewServiceTestsUtils.createTimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 0));

        assertNotNull(commonTimeSlotsResponse);
        assertEquals(candidateName, commonTimeSlotsResponse.getCandidateName());
        assertTrue(commonTimeSlotsResponse.getInterviewersNames().contains(interviewerName));
        assertFalse(commonTimeSlotsResponse.getAvailabilities().isEmpty());
        assertThat(commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots(), hasItem(allOf(
            hasProperty("from", is(expectedTimeSlotOne.getFrom())),
            hasProperty("to", is(expectedTimeSlotOne.getTo()))
        )));
        assertThat(commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots(), hasItem(allOf(
            hasProperty("from", is(expectedTimeSlotTw0.getFrom())),
            hasProperty("to", is(expectedTimeSlotTw0.getTo()))
        )));
        assertEquals(expectedTimeSlotOne , commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots().get(0));
        assertEquals(expectedTimeSlotTw0, commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots().get(1));
    }

    @Test
    public void candidate_and_interviewer_have_two_common_time_slots_different_days_successfully() {
        // Arrange
        LocalTime candidateFromOne = LocalTime.of(9, 0);
        LocalTime candidateToOne = LocalTime.of(18, 0);
        TimeSlot candidateTimeSlotOne = InterviewServiceTestsUtils.createTimeSlot(candidateFromOne, candidateToOne);

        LocalDate candidateDayOne = LocalDate.of(2022, 12, 30);
        Availability candidateAvailabilityOne = InterviewServiceTestsUtils.createAvailability(candidateDayOne, Arrays.asList(candidateTimeSlotOne));

        LocalTime candidateFromTwo = LocalTime.of(9, 0);
        LocalTime candidateToTwo = LocalTime.of(18, 0);
        TimeSlot candidateTimeSlotTwo = InterviewServiceTestsUtils.createTimeSlot(candidateFromTwo, candidateToTwo);

        LocalDate candidateDayTwo = LocalDate.of(2022, 12, 31);
        Availability candidateAvailabilityTwo = InterviewServiceTestsUtils.createAvailability(candidateDayTwo, Arrays.asList(candidateTimeSlotTwo));

        InterviewParticipantAvailability candidateInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(candidateAvailabilityOne, candidateAvailabilityTwo));

        String candidateName = "Candidate";
        Role candidateRole = Role.CANDIDATE;
        InterviewParticipant candidate = new InterviewParticipant(candidateName, candidateRole, candidateInterviewParticipantAvailability);

        InterviewParticipantModel candidateModel = modelMapper.map(candidate, InterviewParticipantModel.class);

        LocalTime interviewerFromOne = LocalTime.of(10, 0);
        LocalTime interviewerToOne = LocalTime.of(11, 0);
        TimeSlot interviewerTimeSlotOne = InterviewServiceTestsUtils.createTimeSlot(interviewerFromOne, interviewerToOne);

        LocalDate interviewerDayOne = LocalDate.of(2022, 12, 30);
        Availability interviewerAvailabilityOne = InterviewServiceTestsUtils.createAvailability(interviewerDayOne, Arrays.asList(interviewerTimeSlotOne));

        LocalTime interviewerFromTwo = LocalTime.of(14, 0);
        LocalTime interviewerToTwo = LocalTime.of(15, 0);
        TimeSlot interviewerTimeSlotTwo = InterviewServiceTestsUtils.createTimeSlot(interviewerFromTwo, interviewerToTwo);

        LocalDate interviewerDayTwo = LocalDate.of(2022, 12, 31);
        Availability interviewerAvailabilityTwo = InterviewServiceTestsUtils.createAvailability(interviewerDayTwo, Arrays.asList(interviewerTimeSlotTwo));

        InterviewParticipantAvailability interviewerInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(interviewerAvailabilityOne, interviewerAvailabilityTwo));

        String interviewerName = "Interviewer";
        Role interviewerRole = Role.INTERVIEWER;
        InterviewParticipant interviewer = new InterviewParticipant(interviewerName, interviewerRole, interviewerInterviewParticipantAvailability);

        InterviewParticipantModel interviewerModel = modelMapper.map(interviewer, InterviewParticipantModel.class);

        // Act
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.ofNullable(candidateModel));
        when(interviewRepository.findById(interviewerName)).thenReturn(Optional.ofNullable(interviewerModel));

        CommonTimeSlotsResponse commonTimeSlotsResponse = interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName, Arrays.asList(interviewerName));

        // Assert
        LocalDate expectedDayOne = LocalDate.of(2022, 12, 30);
        LocalDate expectedDayTwo = LocalDate.of(2022, 12, 31);
        TimeSlot expectedTimeSlotOne = InterviewServiceTestsUtils.createTimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0));
        TimeSlot expectedTimeSlotTw0 = InterviewServiceTestsUtils.createTimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 0));

        assertNotNull(commonTimeSlotsResponse);
        assertEquals(candidateName, commonTimeSlotsResponse.getCandidateName());
        assertTrue(commonTimeSlotsResponse.getInterviewersNames().contains(interviewerName));
        assertFalse(commonTimeSlotsResponse.getAvailabilities().isEmpty());
        assertEquals(expectedDayOne , commonTimeSlotsResponse.getAvailabilities().get(0).getDay());
        assertEquals(expectedDayTwo , commonTimeSlotsResponse.getAvailabilities().get(1).getDay());
        assertThat(commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots(), hasItem(allOf(
            hasProperty("from", is(expectedTimeSlotOne.getFrom())),
            hasProperty("to", is(expectedTimeSlotOne.getTo()))
        )));
        assertThat(commonTimeSlotsResponse.getAvailabilities().get(1).getTimeSlots(), hasItem(allOf(
            hasProperty("from", is(expectedTimeSlotTw0.getFrom())),
            hasProperty("to", is(expectedTimeSlotTw0.getTo()))
        )));
        assertEquals(expectedTimeSlotOne , commonTimeSlotsResponse.getAvailabilities().get(0).getTimeSlots().get(0));
        assertEquals(expectedTimeSlotTw0, commonTimeSlotsResponse.getAvailabilities().get(1).getTimeSlots().get(0));
    }

    @Test
    public void non_existing_candidate_common_time_slots_fails() {
        // Arrange
        String candidateName = "Candidate";
        String interviewerName = "Interviewer";

        // Act && Assert
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.empty());

        BusinessException businessException = assertThrows(BusinessException.class,
                                                           () -> interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName,
                                                                                                                             Arrays.asList(interviewerName)));
        assertEquals("No candidate with name " + candidateName + " found.", businessException.getMessage());
    }

    @Test
    public void non_existing_interviewer_common_time_slots_fails() {
        // Arrange
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(10, 0);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String candidateName = "Candidate";
        Role role = Role.CANDIDATE;
        InterviewParticipant interviewParticipant = new InterviewParticipant(candidateName, role, interviewParticipantAvailability);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        String interviewerName = "Interviewer";

        // Act && Assert
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.ofNullable(interviewParticipantModel));
        when(interviewRepository.findById(interviewerName)).thenReturn(Optional.empty());

        BusinessException businessException = assertThrows(BusinessException.class,
                                                           () -> interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName,
                                                                                                                             Arrays.asList(interviewerName)));
        assertEquals("No interviewer with name " + interviewerName + " found.", businessException.getMessage());
    }

    @Test
    public void candidate_with_invalid_role_common_time_slots_fails() {
        // Arrange
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(10, 0);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String candidateName = "Candidate";
        Role role = Role.INTERVIEWER;
        InterviewParticipant interviewParticipant = new InterviewParticipant(candidateName, role, interviewParticipantAvailability);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        String interviewerName = "Interviewer";

        // Act && Assert
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.ofNullable(interviewParticipantModel));

        BusinessException businessException = assertThrows(BusinessException.class,
                                                           () -> interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName,
                                                                                                                             Arrays.asList(interviewerName)));
        assertEquals("Participant with name " + candidateName + " does not have a candidate role.", businessException.getMessage());
    }

    @Test
    public void interviewer_with_invalid_role_common_time_slots_fails() {
        // Arrange
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(10, 0);
        TimeSlot timeSlot = InterviewServiceTestsUtils.createTimeSlot(from, to);

        LocalDate day = LocalDate.of(2022, 12, 31);
        Availability availability = InterviewServiceTestsUtils.createAvailability(day, Arrays.asList(timeSlot));

        InterviewParticipantAvailability interviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(availability));

        String candidateName = "Candidate";
        Role role = Role.CANDIDATE;
        InterviewParticipant interviewParticipant = new InterviewParticipant(candidateName, role, interviewParticipantAvailability);

        InterviewParticipantModel interviewParticipantModel = modelMapper.map(interviewParticipant, InterviewParticipantModel.class);

        LocalTime interviewerFrom = LocalTime.of(12, 0);
        LocalTime interviewerTo = LocalTime.of(13, 0);
        TimeSlot interviewerTimeSlot = InterviewServiceTestsUtils.createTimeSlot(interviewerFrom, interviewerTo);

        LocalDate interviewerDay = LocalDate.of(2022, 12, 31);
        Availability interviewerAvailability = InterviewServiceTestsUtils.createAvailability(interviewerDay, Arrays.asList(interviewerTimeSlot));

        InterviewParticipantAvailability interviewerInterviewParticipantAvailability = InterviewServiceTestsUtils.createInterviewParticipantAvailability(
            Arrays.asList(interviewerAvailability));

        String interviewerName = "Interviewer";
        Role interviewerRole = Role.CANDIDATE;
        InterviewParticipant interviewer = new InterviewParticipant(interviewerName, interviewerRole, interviewerInterviewParticipantAvailability);

        InterviewParticipantModel interviewerModel = modelMapper.map(interviewer, InterviewParticipantModel.class);

        // Act && Assert
        when(interviewRepository.findById(candidateName)).thenReturn(Optional.ofNullable(interviewParticipantModel));
        when(interviewRepository.findById(interviewerName)).thenReturn(Optional.ofNullable(interviewerModel));

        BusinessException businessException = assertThrows(BusinessException.class,
                                                           () -> interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName,
                                                                                                                             Arrays.asList(interviewerName)));
        assertEquals("Participant with name " + interviewerName + " does not have an interviewer role.", businessException.getMessage());
    }
}
