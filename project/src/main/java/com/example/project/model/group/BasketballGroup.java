package com.example.project.model.group;

import java.util.ArrayList;
import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.game.BasketballGame;
import com.example.project.model.member.BasketballMember;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
public class BasketballGroup extends Group {

    @JsonIgnoreProperties({ "group", "members" })
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    protected List<BasketballGame> games;

    @JsonIgnoreProperties({ "group", "games" })
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    protected List<BasketballMember> members;

    public BasketballGroup() {
        super.sport = Sports.BASKETBALL;
        initVars();
    }

    /* METHODS */

    private void initVars() {
        this.members = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public void addMember(BasketballMember member) {
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

    public void addGame(BasketballGame game) {
        this.games.add(game);
    }

}