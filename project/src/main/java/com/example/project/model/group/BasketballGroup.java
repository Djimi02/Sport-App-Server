package com.example.project.model.group;

import com.example.project.model.Sports;
import com.example.project.model.game.BasketballGame;
import com.example.project.model.member.BasketballMember;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class BasketballGroup extends Group<BasketballMember, BasketballGame> {
    
    public BasketballGroup(String name) {
        super(name, Sports.BASKETBALL);
    }

}