package com.example.project.model.game;

import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FootballGame extends Game {

    @JsonIgnoreProperties({ "games", "members" })
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id", nullable = false)
    private FootballGroup group;

    public FootballGame() {
        super.sport = Sports.FOOTBALL;
    }

}