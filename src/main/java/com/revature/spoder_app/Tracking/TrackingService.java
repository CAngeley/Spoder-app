package com.revature.spoder_app.Tracking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.util.interfaces.Serviceable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService implements Serviceable<Tracking> {
    private final TrackingRepository trackingRepository;

    @Autowired
    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    @Override
    public List<Tracking> findAll() {
        return List.of();
    }

    @Override
    public Tracking create(Tracking newObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Tracking findById(int id) {
        return null;
    }

    @Override
    public Tracking update(Tracking updatedObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Boolean delete(Tracking deletedObject) {
        return null;
    }
}
