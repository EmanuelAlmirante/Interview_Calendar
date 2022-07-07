package com.tamanna.interview_calendar.api;

import com.tamanna.interview_calendar.domain.Availability;
import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.domain.InterviewParticipantAvailability;
import com.tamanna.interview_calendar.domain.response.CommonTimeSlotsResponse;
import com.tamanna.interview_calendar.service.InterviewService;

import jakarta.annotation.Nonnull;
import javax.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/interview-calendar")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/participants")
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewParticipant createParticipant(@RequestBody @Valid @Nonnull InterviewParticipant interviewParticipant) {

        return interviewService.createParticipant(interviewParticipant);
    }

    @GetMapping("/participants")
    @ResponseStatus(HttpStatus.OK)
    public List<InterviewParticipant> getAllInterviewParticipants() {

        return interviewService.getAllInterviewParticipants();
    }

    @GetMapping("/participants/{name}")
    @ResponseStatus(HttpStatus.OK)
    public InterviewParticipant getInterviewParticipant(@PathVariable @Nonnull String name) {

        return interviewService.getInterviewParticipant(name);
    }

    @DeleteMapping("/participants/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterviewParticipant(@PathVariable @Nonnull String name) {

        interviewService.deleteInterviewParticipant(name);
    }

    @GetMapping("/participants/{name}/availabilities")
    @ResponseStatus(HttpStatus.OK)
    public InterviewParticipantAvailability getInterviewParticipantAvailability(@PathVariable @Nonnull String name) {

        return interviewService.getInterviewParticipantAvailability(name);
    }

    @PutMapping("/participants/{name}/availabilities")
    @ResponseStatus(HttpStatus.OK)
    public InterviewParticipantAvailability updateInterviewParticipantAvailability(@PathVariable @Nonnull String name,
                                                                                   @RequestBody @Valid @Nonnull Availability availability) {

        return interviewService.updateInterviewParticipantAvailability(name, availability);
    }

    @DeleteMapping("/participants/{name}/availabilities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterviewParticipantAvailability(@PathVariable @Nonnull String name,
                                                       @RequestBody @Valid @Nonnull Availability availability) {

        interviewService.deleteInterviewParticipantAvailability(name, availability);
    }

    @GetMapping("/common-slots")
    @ResponseStatus(HttpStatus.OK)
    public CommonTimeSlotsResponse getCommonTimeSlotsCandidateAndInterviewers(
        @RequestParam(value = "candidate_name", required = true) @Nonnull String candidateName,
        @RequestParam(value = "interviewer_name", required = true) @Nonnull List<String> interviewersNames) {

        return interviewService.getCommonTimeSlotsCandidateAndInterviewers(candidateName, interviewersNames);
    }
}
