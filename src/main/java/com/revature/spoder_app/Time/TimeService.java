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
        return List.of();
    }

    @Override
    public Time create(Time newObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Time findById(int id) {
        return null;
    }

    @Override
    public Time update(Time updatedObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Boolean delete(Time deletedObject) {
        return null;
    }
}
