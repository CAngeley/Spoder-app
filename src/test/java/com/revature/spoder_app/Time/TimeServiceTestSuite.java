package com.revature.spoder_app.Time;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeServiceTestSuite {

    @Mock
    private TimeRepository mockTimeRepository;

    @InjectMocks
    private TimeService mockTimeService;

    /**
     * This test case tests the create method of the TimeService class
     * The method should return the time that was created
     * @throws JsonProcessingException
     */
    @Test
    public void testCreateTime() throws JsonProcessingException {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));
        when(mockTimeRepository.saveAndFlush(mockTime)).thenReturn(mockTime);

        Time createdTime = mockTimeService.create(mockTime);

        assertEquals(mockTime, createdTime);
        verify(mockTimeRepository).saveAndFlush(mockTime);
        System.out.println("Time created: " + createdTime.toString());
    }

    /**
     * This test case tests the findAll method of the TimeService class
     * The method should return a list of all times
     */
    @Test
    public void testFindAllTimes() {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));
        List<Time> mockTimes = List.of(mockTime, mockTime, mockTime);
        when(mockTimeRepository.findAll()).thenReturn(mockTimes);

        List<Time> foundTimes = mockTimeService.findAll();

        assertEquals(mockTimes.size(), foundTimes.size());
        assertEquals(mockTimes, foundTimes);
        verify(mockTimeRepository).findAll();
        System.out.println("Times found: " + foundTimes);
    }

    /**
     * This test case tests the findById method of the TimeService class
     * The method should return the time with the specified id
     */
    @Test
    public void testFindTimeById() {
        int id = 1;
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setTimeId(id);
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));
        when(mockTimeRepository.findById(id)).thenReturn(java.util.Optional.of(mockTime));

        Time foundTime = mockTimeService.findById(id);

        assertEquals(mockTime, foundTime);
        verify(mockTimeRepository).findById(id);
        System.out.println("Time found: " + foundTime);
    }
}
