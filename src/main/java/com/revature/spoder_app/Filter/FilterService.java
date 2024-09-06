package com.revature.spoder_app.Filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.spoder_app.util.interfaces.Serviceable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService implements Serviceable<Filter> {
    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    @Override
    public List<Filter> findAll() {
        return List.of();
    }

    @Override
    public Filter create(Filter newObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Filter findById(int id) {
        return null;
    }

    @Override
    public Filter update(Filter updatedObject) throws JsonProcessingException {
        return null;
    }

    @Override
    public Boolean delete(Filter deletedObject) {
        return null;
    }
}
