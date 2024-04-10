package com.example.project.model.stats;

import com.example.project.model.Sports;
import com.example.project.model.game.Game;
import com.example.project.model.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Stats<MemberT extends Member<?,?>, GameT extends Game<?>> {

    @Id
    @SequenceGenerator(name = "statsSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "statsSeqGen")
    protected Long id;

    protected String memberName;

    protected Integer wins;
    protected Integer draws;
    protected Integer loses;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_id")
    protected GameT game;

    @JsonIgnoreProperties({ "nickname", "stats", "user", "group", "sport", "isAdmin" })
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "member_id")
    protected MemberT member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Stats() { initVars(); }

    public Stats(Sports sport) {
        this.sport = sport;
        initVars();
    }

    private void initVars() {
        this.wins = 0;
        this.draws = 0;
        this.loses = 0;
    }
    
}