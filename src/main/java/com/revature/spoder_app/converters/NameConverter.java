package com.revature.spoder_app.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.spoder_app.Name;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NameConverter implements AttributeConverter<Name, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method is used to convert a Name object to a JSON string.
     * @param name The Name object to convert.
     * @return The JSON string that was converted from the Name object.
     */
    @Override
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

    /**
     * This method is used to convert a JSON string to a Name object.
     * @param dbData The JSON string to convert.
     * @return The Name object that was converted from the JSON string.
     */
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