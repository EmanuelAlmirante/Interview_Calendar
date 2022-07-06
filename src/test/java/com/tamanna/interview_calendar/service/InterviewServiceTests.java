package com.tamanna.interview_calendar.service;

import com.tamanna.interview_calendar.repository.InterviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTests {

    @Mock
    private InterviewRepository interviewRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InterviewService interviewService;

    @Test
    public void create_participant_successfully() {
        // Arrange

        // Act

        // Assert
    }

}
