package com.example.project.model.game;

import java.time.LocalDate;

import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FootballGame extends Game<FootballGroup> {

    public FootballGame(LocalDate date, FootballGroup group) {
        super(date, Sports.FOOTBALL, group);

    }

}