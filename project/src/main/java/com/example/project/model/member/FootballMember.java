package com.example.project.model.member;


import com.example.project.model.Sports;
import com.example.project.model.group.FootballGroup;
import com.example.project.model.stats.FBStats;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FootballMember extends Member {

    @JsonIgnoreProperties({ "game" })
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "stats_id")
    protected FBStats stats;

    @JsonIgnoreProperties({ "members", "games" })
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    protected FootballGroup group;

    public FootballMember() {
        super.sport = Sports.FOOTBALL;
        this.stats = new FBStats();
    }

    // @PostLoad
    // private void postLoad(){
    //     this.groupAbs = this.group;
    //     this.statsAbs = this.stats;
    // }
}