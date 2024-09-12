package com.revature.spoder_app.Tracking;

import com.revature.spoder_app.Task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackings")
public class TrackingController {
    private final TrackingService trackingService;

    @Autowired
    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping("/new")
    public ResponseEntity<Object> postNewTracking(@RequestHeader int userId, @RequestBody Task task) {
        try {
            return ResponseEntity.status(201).body(trackingService.create(userId, task));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}
