package com.example.project.model.game;

import java.util.Date;

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
public abstract class Game {
    
    @Id
    @SequenceGenerator(name = "gameSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "gameSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected Date date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Game(Date date, Sports sport) {
        this.date = date;
        this.sport = sport;
    }

}