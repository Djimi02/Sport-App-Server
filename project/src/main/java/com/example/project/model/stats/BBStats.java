package com.example.project.model.stats;

import com.example.project.model.Sports;
import com.example.project.model.game.BasketballGame;
import com.example.project.model.member.BasketballMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class BBStats extends Stats {
    
    private int points;
    private int numberOfThreePoints;
    private int numOfDunks;
    private int blocks;
    private int fouls;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_id")
    private BasketballGame game;

    @JsonIgnoreProperties({ "nickname", "stats", "user", "group", "sport", "isAdmin" })
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "member_id")
    private BasketballMember member;

    public BBStats() {
        super.sport = Sports.BASKETBALL;
        initVars();
    }

    private void initVars() {
        this.points = 0;
        this.numberOfThreePoints = 0;
        this.blocks = 0;
        this.fouls = 0;
        this.numOfDunks = 0;
    }

}