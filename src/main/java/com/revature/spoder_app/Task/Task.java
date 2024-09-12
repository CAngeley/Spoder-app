package com.revature.spoder_app.Task;

import com.revature.spoder_app.Filter.Filter;
import com.revature.spoder_app.Name;
import com.revature.spoder_app.Note;
import com.revature.spoder_app.Time.Time;
import com.revature.spoder_app.User.User;
import com.revature.spoder_app.converters.NameConverter;
import com.revature.spoder_app.converters.NoteConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "json")
    @Convert(converter = NameConverter.class)
    @JoinColumn(name = "task_name_id", nullable = false)
    private Name taskName;

    @ManyToOne
    @JoinColumn(name = "task_time_id", nullable = false)
    private Time taskTime;

    @ManyToOne
    @JoinColumn(name = "project_filter_id")
    private Filter projectFilter;

    @ManyToOne
    @JoinColumn(name = "client_filter_id")
    private Filter clientFilter;

    @ManyToMany
    @JoinTable(
            name = "task_filters",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "filter_id")
    )
    private Set<Filter> categoryFilter;

    @Column(columnDefinition = "json")
    @Convert(converter = NoteConverter.class)
    @JoinColumn(name = "task_note_id", nullable = false)
    private Note taskNote;
}