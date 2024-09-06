package com.revature.spoder_app.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.spoder_app.Note;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NoteConverter implements AttributeConverter<Note, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(Note note) {
        if(note == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(note);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Note to JSON string", e);
        }
    }

    @Override
    public Note convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, Note.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to Note object", e);
        }
    }
}