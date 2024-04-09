package com.example.project.model.stats;

import com.example.project.model.Sports;
import com.example.project.model.game.FootballGame;
import com.example.project.model.member.FootballMember;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class FBStats extends Stats<FootballMember, FootballGame> {

    private int goals;
    private int assists;
    private int saves;
    private int fouls;
    private Boolean isPartOfTeam1; // used for games

    public FBStats() {
        super(Sports.FOOTBALL);
        initVars();
    }

    private void initVars() {
        this.goals = 0;
        this.assists = 0;
        this.saves = 0;
        this.fouls = 0;
        this.isPartOfTeam1 = null;
    }
}
