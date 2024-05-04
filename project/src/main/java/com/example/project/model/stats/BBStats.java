package com.example.project.model.stats;

import com.example.project.model.Sports;
import com.example.project.model.game.BasketballGame;
import com.example.project.model.member.BasketballMember;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class BBStats extends Stats<BasketballMember, BasketballGame> {
    
    private int points;
    private int numberOfThreePoints;
    private int blocks;
    private int fouls;
    private Boolean isPartOfTeam1; // used for games

    public BBStats() {
        super(Sports.BASKETBALL);
        initVars();
    }

    private void initVars() {
        this.points = 0;
        this.numberOfThreePoints = 0;
        this.blocks = 0;
        this.fouls = 0;
    }

}