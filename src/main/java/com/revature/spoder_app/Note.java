package com.revature.spoder_app;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("note_id")
    private int noteId;
    @JsonProperty("note_header")
    private String noteHeader;
    @JsonProperty("note_body")
    private String noteBody;
    @JsonProperty("note_type")
    @Column(name = "note_type", columnDefinition = "varchar(5) default 'TEXT'")
    @Enumerated(EnumType.STRING)
    private NoteType noteType;

    public enum NoteType {
        TEXT, CODE, IMAGE
    }
}
