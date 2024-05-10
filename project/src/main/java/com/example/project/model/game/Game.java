package com.example.project.model.game;

import java.time.LocalDate;
import java.util.Calendar;

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
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public abstract class Game {

    @Id
    @SequenceGenerator(name = "gameSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "gameSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected LocalDate date;

    protected String results;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Game() {
        Calendar calendar = Calendar.getInstance();
        this.date = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

}