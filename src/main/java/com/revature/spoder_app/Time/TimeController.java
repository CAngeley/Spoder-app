package com.revature.spoder_app.Time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Posts a new time to the database
     * @param time The time to be added to the database
     * @return A response entity with the time that was added to the database
     */
    //TODO: Might need to change access so it is used for only testing
    @PostMapping("/new/time")
    public ResponseEntity<Object> postNewTime(@RequestBody Time time) {
        try {
            if (time == null || time.getStartTime() == null) {
                return ResponseEntity.status(400).body("Error: Time object is null or missing required fields");
            }
            return ResponseEntity.status(201).body(timeService.create(time));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    /**
     * Posts a new time to the database creating a new time object
     * @return A response entity with the time that was added to the database
     */
    @PostMapping("/new")
    public ResponseEntity<Object> postNewTime() {
        try {
            Time time = new Time();
            time.setStartTime(LocalDateTime.now());
            return ResponseEntity.status(201).body(timeService.create(time));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all times from the database. Only users with the ADMIN role can access this endpoint.
     * @return A response entity with a list of all times in the database
     */
    @GetMapping("/all")
    public ResponseEntity<List<Time>> getAllTimes(@RequestHeader User.UserType userType) {
        if (userType != User.UserType.ADMIN) {
            return ResponseEntity.status(403).build();
        }
        List<Time> times = timeService.findAll();
        if (times.isEmpty()) {
            return ResponseEntity.status(204).body(times);
        }
        return ResponseEntity.ok(times);
    }

    /**
     * Retrieves a time by its id
     * @param id The id of the time to be retrieved
     * @return A response entity with the time that was retrieved
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Time> getTimeById(@RequestHeader User.UserType userType, @RequestHeader int userId, @PathVariable int id) {
        if (userType != User.UserType.ADMIN && userId != id) {
            return ResponseEntity.status(403).build();
        }
        Time time = timeService.findById(id);
        if (time == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(time);
    }

    @PutMapping("/update/end-time")
    public ResponseEntity<Object> updateEndTime(@RequestBody int id) throws JsonProcessingException {
        //TODO: When User and Time is connected through a relationship, this will need to be updated to use the user id to find the time
        Time time = timeService.findById(id);
        if (time == null) {
            return ResponseEntity.status(404).build();
        }
        time.setEndTime(LocalDateTime.now());
        try {
            return ResponseEntity.ok(timeService.update(time));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}
