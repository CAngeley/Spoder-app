package com.revature.spoder_app.Filter;

import com.revature.spoder_app.Name;
import com.revature.spoder_app.converters.NameConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "filters")
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int filterId;

    @Column(columnDefinition = "json")
    @Convert(converter = NameConverter.class)
    @JoinColumn(name = "filter_name_id", nullable = false)
    private Name filterName;

    @Column(name = "filter_type", columnDefinition = "varchar(8) default 'CATEGORY'")
    @Enumerated(EnumType.STRING)
    private filterType filterType;

    public enum filterType {
        PROJECT, CLIENT, CATEGORY
    }
}