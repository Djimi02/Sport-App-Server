package com.example.project.model.member;

import com.example.project.model.Sports;
import com.example.project.model.group.BasketballGroup;
import com.example.project.model.stats.BBStats;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BasketballMember extends Member<BasketballGroup, BBStats> {
    
    public BasketballMember() {
        setSport(Sports.BASKETBALL);
        setStats(new BBStats());
    }

    public BasketballMember(String nickname, BasketballGroup group) {
        super(nickname, Sports.BASKETBALL, group, new BBStats());
    }

}