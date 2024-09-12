package com.revature.spoder_app.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.spoder_app.Note;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NoteConverter implements AttributeConverter<Note, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method is used to convert a Note object to a JSON string.
     * @param note The Note object to convert.
     * @return The JSON string that was converted from the Note object.
     */
    @Override
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

    /**
     * This method is used to convert a JSON string to a Note object.
     * @param dbData The JSON string to convert.
     * @return The Note object that was converted from the JSON string.
     */
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