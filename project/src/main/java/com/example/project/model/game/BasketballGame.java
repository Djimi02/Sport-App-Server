package com.example.project.model.game;

import java.time.LocalDate;

import com.example.project.model.Sports;
import com.example.project.model.group.BasketballGroup;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class BasketballGame extends Game<BasketballGroup> {

    public BasketballGame(LocalDate date, BasketballGroup group) {
        super(date, Sports.BASKETBALL, group);
    }
}
