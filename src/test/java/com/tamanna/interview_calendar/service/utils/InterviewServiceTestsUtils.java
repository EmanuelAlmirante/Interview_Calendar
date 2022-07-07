package com.tamanna.interview_calendar.service.utils;

import com.tamanna.interview_calendar.common.Role;
import com.tamanna.interview_calendar.domain.Availability;
import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.InterviewParticipantAvailability;
import com.tamanna.interview_calendar.domain.TimeSlot;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InterviewServiceTestsUtils {

    @Nonnull
    public static InterviewParticipant createInterviewParticipant(@Nonnull String name,
                                                                  @Nonnull Role role,
                                                                  @Nullable InterviewParticipantAvailability interviewParticipantAvailability) {

        return new InterviewParticipant(name, role, interviewParticipantAvailability);
    }

    @Nonnull
    public static InterviewParticipantAvailability createInterviewParticipantAvailability(@Nullable List<Availability> availabilities) {

        return new InterviewParticipantAvailability(availabilities);
    }

    @Nonnull
    public static Availability createAvailability(@Nonnull LocalDate day, @Nullable List<TimeSlot> timeSlots) {

        return new Availability(day, timeSlots);
    }

    @Nonnull
    public static TimeSlot createTimeSlot(@Nonnull LocalTime from, @Nonnull LocalTime to) {

        return new TimeSlot(from, to);
    }
}
