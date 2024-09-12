package com.revature.spoder_app.Time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TimeControllerTestSuite {

    @Mock
    private TimeService mockTimeService;

    @InjectMocks
    private TimeController mockTimeController;

    /**
     * Tests the postNewTime method in the TimeController
     * Expect a 201 status code
     */
    @Test
    public void testPostNewTime() throws JsonProcessingException {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));
        when(mockTimeService.create(mockTime)).thenReturn(mockTime);

        ResponseEntity<Object> response = mockTimeController.postNewTime(mockTime);

        assertEquals(201, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(mockTime, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockTimeService).create(mockTime);
    }

    /**
     * Tests the postNewTime method in the TimeController with no start time
     * Expect a 400 status code
     */
    @Test
    public void testPostNewTimeNoStartTime() throws JsonProcessingException {
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setEndTime(LocalDateTime.parse(endTime));

        ResponseEntity<Object> response = mockTimeController.postNewTime(mockTime);

        assertEquals(400, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("Error: Time object is null or missing required fields", response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the postNewTime method in the TimeController with no end time
     * Expect a 201 status code
     */
    @Test
    public void testPostNewTimeNoEndTime() throws JsonProcessingException {
        String startTime = "2021-07-01T12:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        when(mockTimeService.create(mockTime)).thenReturn(mockTime);

        ResponseEntity<Object> response = mockTimeController.postNewTime(mockTime);

        assertEquals(201, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(mockTime, response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the postNewTime method in the TimeController with no start or end time
     * Expect a 400 status code
     */
    @Test
    public void testPostNewTimeEmptyTime() throws JsonProcessingException {
        Time mockTime = new Time();

        ResponseEntity<Object> response = mockTimeController.postNewTime(mockTime);

        assertEquals(400, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("Error: Time object is null or missing required fields", response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the postNewTime method in the TimeController with a null time
     * Expect a 400 status code
     */
    @Test
    public void testPostNewTimeNullTime() throws JsonProcessingException {
        Time mockTime = null;
        ResponseEntity<Object> response = mockTimeController.postNewTime(mockTime);

        assertEquals(400, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals("Error: Time object is null or missing required fields", response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the getAllTimes method in the TimeController
     * Expect a 200 status code
     */
    @Test
    public void testGetAllTimes() {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));

        List<Time> times = List.of(mockTime, mockTime);
        when(mockTimeService.findAll()).thenReturn(times);

        ResponseEntity<List<Time>> response = mockTimeController.getAllTimes(User.UserType.ADMIN);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(times, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockTimeService).findAll();
    }

    /**
     * Tests the getAllTimes method in the TimeController with a non-admin user
     * Expect a 403 status code
     */
    @Test
    public void testGetAllTimesNotAdmin() {
        ResponseEntity<List<Time>> response = mockTimeController.getAllTimes(User.UserType.CUSTOMER);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(null, response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the getAllTimes method in the TimeController with an empty list of times
     * Expect a 204 status code
     */
    @Test
    public void testGetAllTimesEmptyList() {
        List<Time> times = List.of();
        when(mockTimeService.findAll()).thenReturn(times);

        ResponseEntity<List<Time>> response = mockTimeController.getAllTimes(User.UserType.ADMIN);

        assertEquals(204, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(times, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockTimeService).findAll();
    }

    /**
     * Tests the getTimeById method in the TimeController
     * Expect a 200 status code
     */
    @Test
    public void testGetTimeById() {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));

        when(mockTimeService.findById(1)).thenReturn(mockTime);

        ResponseEntity<Time> response = mockTimeController.getTimeById(User.UserType.CUSTOMER, 1, 1);

        assertEquals(200, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertEquals(mockTime, response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockTimeService).findById(1);
    }

    /**
     * Tests the getTimeById method in the TimeController with a non-admin user
     * Expect a 403 status code
     */
    @Test
    public void testGetTimeByIdNotAdmin() {
        ResponseEntity<Time> response = mockTimeController.getTimeById(User.UserType.CUSTOMER, 2, 1);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertNull(response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the getTimeById method in the TimeController with a non-matching user id
     * Expect a 403 status code
     */
    @Test
    public void testGetTimeByIdNotMatchingUserId() {
        ResponseEntity<Time> response = mockTimeController.getTimeById(User.UserType.CUSTOMER, 1, 2);

        assertEquals(403, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertNull(response.getBody());
        System.out.println("Response Body: " + response.getBody());
    }

    /**
     * Tests the getTimeById method in the TimeController class and no time is found
     * Expect a 404 status code
     */
    @Test
    public void testGetTimeByIdNotFound() {
        when(mockTimeService.findById(3)).thenReturn(null);

        ResponseEntity<Time> response = mockTimeController.getTimeById(User.UserType.ADMIN, 1, 3);

        assertEquals(404, response.getStatusCode().value());
        System.out.println("Response Code: " + response.getStatusCode().value());
        assertNull(response.getBody());
        System.out.println("Response Body: " + response.getBody());
        verify(mockTimeService).findById(3);
    }
}
