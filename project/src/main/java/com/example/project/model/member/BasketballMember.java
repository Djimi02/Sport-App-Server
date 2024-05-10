package com.example.project.model.member;

import com.example.project.model.Sports;
import com.example.project.model.group.BasketballGroup;
import com.example.project.model.stats.BBStats;
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

@Getter
@Setter
@Entity
public class BasketballMember extends Member {

    @JsonIgnoreProperties({ "game" })
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "stats_id")
    protected BBStats stats;

    @JsonIgnoreProperties({ "members", "games" })
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    protected BasketballGroup group;
    
    public BasketballMember() {
        super.sport = Sports.BASKETBALL;
        this.stats = new BBStats();
    }

    @PostLoad
    private void postLoad(){
        this.groupAbs = this.group;
    }

}