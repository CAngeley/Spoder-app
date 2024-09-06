package com.revature.spoder_app.Time;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "times")
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeId;
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Transient
    private Duration totalTime;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateTotalTime() {
        if (startTime != null && endTime != null) {
            this.totalTime = Duration.between(startTime, endTime);
        }
    }
}
