package com.revature.spoder_app.Tracking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.Task.Task;
import com.revature.spoder_app.Task.TaskRepository;
import com.revature.spoder_app.User.User;
import com.revature.spoder_app.User.UserRepository;
import com.revature.spoder_app.util.interfaces.Serviceable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService implements Serviceable<Tracking> {
    private final TrackingRepository trackingRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TrackingService(TrackingRepository trackingRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.trackingRepository = trackingRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
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

    /**
     * Creates a new tracking in the database
     *  User must exist in the database
     *  Task is saved to the database
     *  Tracking is saved to the database
     * @param userId The id of the user to create the tracking for
     * @param task The task to create the tracking for
     * @return The tracking that was created
     */
    public Object create(int userId, Task task) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Task savedTask = taskRepository.saveAndFlush(task);
        return trackingRepository.saveAndFlush(new Tracking(user, savedTask));
    }
}
