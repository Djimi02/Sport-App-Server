package com.example.project.model.member;

import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.game.FootballGame;
import com.example.project.model.group.FootballGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class FootballMember extends Member {

    // @JsonIgnoreProperties({"members"})
    // @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    // @JoinColumn(name="group_id", nullable = false)
    // private FootballGroup group;

    @JsonIgnoreProperties({"members"})
    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<FootballGame> games;
    
    @Column(nullable = false)
    private int numOfGoalsScored;

    public FootballMember(String nickname, FootballGroup group) {
        super(nickname, Sports.FOOTBALL, group);

        initVars();
    }

    private void initVars() {
        this.numOfGoalsScored = 0;
    }
}