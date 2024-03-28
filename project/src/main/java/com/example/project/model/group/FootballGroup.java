package com.example.project.model.group;

import com.example.project.model.Sports;
import com.example.project.model.game.FootballGame;
import com.example.project.model.member.FootballMember;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FootballGroup extends Group<FootballMember, FootballGame> {

    public FootballGroup(String name) {
        super(name, Sports.FOOTBALL);
    }
    
}