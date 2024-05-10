package com.example.project.model.group;

import java.util.ArrayList;
import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.game.FootballGame;
import com.example.project.model.member.FootballMember;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FootballGroup extends Group {

    @JsonIgnoreProperties({ "group", "groupAbs", "members" })
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    protected List<FootballGame> games;

    @JsonIgnoreProperties({ "group", "groupAbs", "games" })
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    protected List<FootballMember> members;

    public FootballGroup() {
        super.sport = Sports.FOOTBALL;
        initVars();
    }

    /* METHODS */

    private void initVars() {
        this.members = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public void addMember(FootballMember member) {
        this.members.add(member);
    }

    public void removeMember(Long memberID) {
        int memberToBeRemoved = -1;
        for (int i = 0; i < this.members.size(); i++) {
            if (this.members.get(i).getId() == memberID) {
                memberToBeRemoved = i;
                break;
            }
        }
        if (memberToBeRemoved != -1) {
            this.members.remove(memberToBeRemoved);
        }
    }

    public void addGame(FootballGame game) {
        this.games.add(game);
    }
    
}