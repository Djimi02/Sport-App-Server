package com.example.project.model.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FootballGame extends Game {

    @JsonIgnoreProperties({"games"})
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="group_id", nullable = false)
    private FootballGroup group;

    @JsonIgnoreProperties({"games"})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "football_games_played", 
        joinColumns = @JoinColumn(name = "game_id"), 
        inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<FootballMember> members;

    /* FOOTBALL GAME STATS */

    public FootballGame(Date date, FootballGroup group) {
        super(date, Sports.FOOTBALL);
        this.group = group;

        initVars();
    }

    private void initVars() {
        this.members = new ArrayList<>();
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

}
