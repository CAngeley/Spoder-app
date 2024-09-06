package com.revature.spoder_app.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.spoder_app.Name;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NameConverter implements AttributeConverter<Name, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    // takes in CurrentCondition instance and serializes it into a json string to store in database
    public String convertToDatabaseColumn(Name name) {
        if(name == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(name);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Name to JSON string", e);
        }
    }

    @Override
    public Name convertToEntityAttribute(String dbData) {
        if(dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, Name.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON string to Name object", e);
        }
    }
}