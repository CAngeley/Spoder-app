package com.revature.spoder_app.Task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.util.interfaces.Serviceable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements Serviceable<Task> {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return List.of();
    }

    @Override
    public Task create(Task newObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Task findById(int id) {
        return null;
    }

    @Override
    public Task update(Task updatedObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Boolean delete(Task deletedObject) {
        return null;
    }
}
