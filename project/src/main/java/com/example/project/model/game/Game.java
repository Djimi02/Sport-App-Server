package com.example.project.model.game;

import java.time.LocalDate;

import com.example.project.model.Sports;
import com.example.project.model.group.Group;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public abstract class Game<GroupT extends Group<?, ?>> {

    @Id
    @SequenceGenerator(name = "gameSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "gameSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected LocalDate date;

    protected String results;
    private Integer victory = null; // -1 -> team 1 won, 0 -> draw, 1 -> team 2 won

    @JsonIgnoreProperties({ "games", "members" })
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupT group;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Game() {
    }

    public Game(LocalDate date, Sports sport, GroupT group) {
        this.date = date;
        this.sport = sport;
        this.group = group;
        this.victory = null;
    }

}