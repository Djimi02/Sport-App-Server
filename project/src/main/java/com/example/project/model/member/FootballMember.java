package com.example.project.model.member;


import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

// @Data
@Getter
@Setter
@Entity
public class FootballMember extends Member<FootballGroup> {

    private int goals;
    private int assists;
    private int saves;
    private int fouls;
    private Boolean isPartOfTeam1; // used for games 

    public FootballMember() {
        setSport(Sports.FOOTBALL);
        initVars();
    }

    public FootballMember(String nickname, FootballGroup group) {
        super(nickname, Sports.FOOTBALL, group);

        initVars();
    }

    // Used for custum query in FootballGameRepository
    public FootballMember(String nickname, int goals, int assists, int saves, int fouls, Boolean isPartOfTeam1) {
        super(nickname, Sports.FOOTBALL, null);
        this.goals = goals;
        this.assists = assists;
        this.saves = saves;
        this.fouls = fouls;
        this.isPartOfTeam1 = isPartOfTeam1;
    }

    private void initVars() {
        this.goals = 0;
        this.assists = 0;
        this.saves = 0;
        this.fouls = 0;
        this.isPartOfTeam1 = null;
    }
}