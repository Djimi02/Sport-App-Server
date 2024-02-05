package com.example.project.model.member;

import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class FootballMember extends Member {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="group_id", nullable = false)
    private FootballGroup group;

    @ManyToMany
    @JoinTable(
        name = "football_games_played", 
        joinColumns = @JoinColumn(name = "member_id"), 
        inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<FootballGame> games;
    
    @Column(nullable = false)
    private int numOfGoalsScored;

    public FootballMember(String nickname, FootballGroup group) {
        super(nickname, Sports.FOOTBALL);
        this.group = group;

        initVars();
    }

    private void initVars() {
        this.numOfGoalsScored = 0;
    }
}