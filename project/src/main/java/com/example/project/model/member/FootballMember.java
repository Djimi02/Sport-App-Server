package com.example.project.model.member;


import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.stats.FBStats;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FootballMember extends Member<FootballGroup, FBStats> {

    public FootballMember() {
        setSport(Sports.FOOTBALL);
    }

    public FootballMember(String nickname, FootballGroup group) {
        super(nickname, Sports.FOOTBALL, group, new FBStats());
    }
}