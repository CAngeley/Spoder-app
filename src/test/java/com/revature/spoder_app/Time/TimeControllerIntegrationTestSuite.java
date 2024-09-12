package com.revature.spoder_app.Time;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.spoder_app.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TimeControllerIntegrationTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeRepository timeRepository;

    HttpHeaders headersAdmin;
    HttpHeaders headersCustomer;

    @BeforeEach
    public void setUp() {
        headersAdmin = new HttpHeaders();
        headersAdmin.add("userId", "1");
        headersAdmin.add("userType", User.UserType.ADMIN.toString());

        headersCustomer = new HttpHeaders();
        headersCustomer.add("userId", "2");
        headersCustomer.add("userType", User.UserType.CUSTOMER.toString());
    }

    /**
     * This test case tests the postNewTimeEndpoint method in the TimeController
     * @throws Exception
     */
    @Test
    public void testPostNewTimeEndpoint() throws Exception {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        String inputJson = String.format("""
            {
                "startTime": "%s",
                "endTime": "%s"
            }
            """, startTime, endTime);
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));

        when(timeRepository.saveAndFlush(mockTime)).thenReturn(mockTime);

        mockMvc.perform(post("/time/new")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(inputJson))
                .andExpect(status().isCreated());
    }

    /**
     * This test case tests the getAllTimes method in the TimeController
     * @throws Exception
     */
    @Test
    public void testGetAllTimesEndpoint() throws Exception {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));
        List<Time> times = List.of(mockTime, mockTime);

        when(timeRepository.findAll()).thenReturn(times);

        mockMvc.perform(get("/time/all")
                .headers(headersAdmin))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * This test case verifies that the getAllTimes endpoint is forbidden for customers
     * @throws Exception
     */
    @Test
    public void testGetAllTimesEndpointForbidden() throws Exception {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));
        List<Time> times = List.of(mockTime, mockTime);

        when(timeRepository.findAll()).thenReturn(times);

        mockMvc.perform(get("/time/all")
                        .headers(headersCustomer))
                .andExpect(status().isForbidden());
    }

    /**
     * This test case verifies that the getTimeById endpoint is working
     * @throws Exception
     */
    @Test
    public void testGetTimeByIdEndpoint() throws Exception {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));

        when(timeRepository.findById(1)).thenReturn(Optional.of(mockTime));

        mockMvc.perform(get("/time/id/1")
                        .headers(headersAdmin))
                .andExpect(status().isOk());
    }

    /**
     * This test case verifies that the getTimeById endpoint is forbidden for customers
     * @throws Exception
     */
    @Test
    public void testGetTimeByIdEndpointForbidden() throws Exception {
        String startTime = "2021-07-01T12:00:00";
        String endTime = "2021-07-01T13:00:00";
        Time mockTime = new Time();
        mockTime.setStartTime(LocalDateTime.parse(startTime));
        mockTime.setEndTime(LocalDateTime.parse(endTime));

        when(timeRepository.findById(1)).thenReturn(Optional.of(mockTime));

        mockMvc.perform(get("/time/id/1")
                        .headers(headersCustomer))
                .andExpect(status().isForbidden());
    }


}
