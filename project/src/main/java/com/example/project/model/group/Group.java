package com.example.project.model.group;

import java.util.UUID;

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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_group")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public abstract class Group {

    @Id
    @SequenceGenerator(name = "groupSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "groupSeqGen")
    protected Long id;

    @Column(nullable = false, unique = true)
    protected UUID uuid;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Group() {
        this.uuid = UUID.randomUUID();
    }

}