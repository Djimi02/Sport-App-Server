package com.example.project.model.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.member.FootballMember;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FootballGame extends Game {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="group_id", nullable = false)
    private FootballGroup group;

    @ManyToMany(mappedBy = "games")
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

}
