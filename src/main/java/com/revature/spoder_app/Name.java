package com.revature.spoder_app;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Name {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("name_id")
    private int nameId;
    @JsonProperty("primary_name")
    private String primaryName;
    @JsonProperty("secondary_name")
    private String secondaryName;
    @JsonProperty("tertiary_name")
    private String tertiaryName;
}
