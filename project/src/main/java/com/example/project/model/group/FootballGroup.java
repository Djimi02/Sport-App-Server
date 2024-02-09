package com.example.project.model.group;

import java.util.ArrayList;
import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.game.FootballGame;
import com.example.project.model.member.FootballMember;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FootballGroup extends Group {

    @JsonIgnoreProperties({"group"})
    @OneToMany(mappedBy="group", fetch = FetchType.EAGER)
    protected List<FootballGame> games;

    @JsonIgnoreProperties({"group"})
    @OneToMany(mappedBy="group", fetch = FetchType.EAGER)
    protected List<FootballMember> members;
    
    // TODO: ADD MORE GROUP STATS VARS

    public FootballGroup(String name) {
        super(name, Sports.FOOTBALL);

        initVars();
    }

    private void initVars() {
        this.members = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    /* METHODS */

    public void addMember(FootballMember member) {
        this.members.add(member);
    }

    public void removeMember(FootballMember member) {
        int memberToBeRemoved = -1;
        for (int i = 0; i < this.members.size(); i++) {
            if (this.members.get(i).getId() == member.getId()) {
                memberToBeRemoved = i;
                break;
            }
        }
        if (memberToBeRemoved != -1) {
            this.members.remove(memberToBeRemoved);
        }
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

    public void removeGame(FootballGame game) {
        int gameToBeRemoved = -1;
        for (int i = 0; i < games.size(); i++) {
            if (this.members.get(i).getId() == game.getId()) {
                gameToBeRemoved = i;
                break;
            }
        }
        if (gameToBeRemoved != -1) {
            this.games.remove(gameToBeRemoved);
        }
    }
    
}