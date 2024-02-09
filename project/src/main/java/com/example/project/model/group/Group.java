package com.example.project.model.group;

import com.example.project.model.Sports;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public abstract class Group {

    @Id
    @SequenceGenerator(name = "groupSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "groupSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Group(String name, Sports sport) {
        this.name = name;
        this.sport = sport;
    }

}