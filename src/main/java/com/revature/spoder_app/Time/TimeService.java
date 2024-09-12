package com.revature.spoder_app.Time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.util.interfaces.Serviceable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeService implements Serviceable<Time> {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Override
    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    @Override
    public Time create(Time time) throws JsonProcessingException {
        if (!isStartTimeBeforeEndTime(time)) {
            throw new IllegalArgumentException("End time is before start time");
        }
        return timeRepository.saveAndFlush(time);
    }

    @Override
    public Time findById(int id) {
        return timeRepository.findById(id).orElse(null);
    }

    @Override
    public Time update(Time time) throws JsonProcessingException {
        if (!isStartTimeBeforeEndTime(time)) {
            throw new IllegalArgumentException("End time is before start time");
        }
        return timeRepository.saveAndFlush(time);
    }

    @Override
    public Boolean delete(Time deletedObject) {
        return null;
    }

    private Boolean isStartTimeBeforeEndTime(Time time) {
        if (time.getStartTime() == null) {
            return false; // If there is no start time, then the time is invalid
        } else if (time.getEndTime() == null) {
            return true; // If there is no end time but there is a start time, then the time is valid
        }
        return time.getStartTime().isBefore(time.getEndTime()); // If there is a start and end time, then the start time must be before the end time
    }
}
