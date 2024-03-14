package com.example.project.model.member;


import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class FootballMember extends Member {

    private int goals;
    private int assists;
    private int saves;
    private int fouls;
    private Boolean isPartOfTeam1; // used for games 

    public FootballMember() {
        setSport(Sports.FOOTBALL);
    }

    public FootballMember(String nickname, FootballGroup group) {
        super(nickname, Sports.FOOTBALL, group);

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