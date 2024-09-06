package com.revature.spoder_app.util.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface Serviceable<O> {
    List<O> findAll();
    O create(O newObject) throws JsonProcessingException;
    O findById(int id);
    O update(O updatedObject) throws JsonProcessingException;
    Boolean delete(O deletedObject);
}