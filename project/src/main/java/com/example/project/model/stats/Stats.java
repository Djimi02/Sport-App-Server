package com.example.project.model.stats;

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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Stats {

    @Id
    @SequenceGenerator(name = "statsSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "statsSeqGen")
    protected Long id;

    protected String memberName;

    protected Integer wins;
    protected Integer draws;
    protected Integer loses;
    protected Boolean isPartOfTeam1; // used for games

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Stats() {
        initVars();
    }

    private void initVars() {
        this.wins = 0;
        this.draws = 0;
        this.loses = 0;
        this.isPartOfTeam1 = null;
    }
    
}