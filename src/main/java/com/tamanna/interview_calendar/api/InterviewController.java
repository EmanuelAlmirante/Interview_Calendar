package com.tamanna.interview_calendar.api;

import com.tamanna.interview_calendar.domain.InterviewParticipant;
import com.tamanna.interview_calendar.service.InterviewService;
import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/interview-calendar")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/participants")
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewParticipant createParticipant(@RequestBody @Nonnull InterviewParticipant interviewParticipant) {

        return interviewService.createParticipant(interviewParticipant);
    }

    @GetMapping("/participants")
    @ResponseStatus(HttpStatus.OK)
    public List<InterviewParticipant> getAllInterviewParticipants() {

        return null;
    }

    @GetMapping("/participants/{name}")
    @ResponseStatus(HttpStatus.OK)
    public InterviewParticipant getInterviewParticipant() {

        return null;
    }

    @DeleteMapping("/participants/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterviewParticipant() {

    }

    @GetMapping("/participants/{name}/availability")
    @ResponseStatus(HttpStatus.OK)
    public InterviewParticipant getInterviewParticipantAvailability() {

        return null;
    }

    @PatchMapping("/participants/{name}/availability")
    @ResponseStatus(HttpStatus.OK)
    public InterviewParticipant updateInterviewParticipantAvailability() {

        return null;
    }

    @DeleteMapping("/participants/{name}/availability")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInterviewParticipantAvailability() {

    }

//    @GetMapping("")

}
