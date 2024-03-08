package com.example.project.model.member;

import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class FootballMember extends Member {

    @JsonIgnoreProperties({ "members" })
    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<FootballGame> games;

    private int goals;
    private int assists;
    private int saves;
    private int fouls;

    public FootballMember(String nickname, FootballGroup group) {
        super(nickname, Sports.FOOTBALL, group);

        initVars();
    }

    private void initVars() {
        this.goals = 0;
        this.assists = 0;
        this.saves = 0;
        this.fouls = 0;
    }
}