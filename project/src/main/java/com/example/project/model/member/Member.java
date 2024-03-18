package com.example.project.model.member;

import com.example.project.model.Sports;
import com.example.project.model.User;
import com.example.project.model.group.Group;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Member {

    @Id
    @SequenceGenerator(name = "memberSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "memberSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected String nickname;

    @JsonIgnoreProperties({"members"})
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    protected User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    @JsonIgnoreProperties({"members", "games"})
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="group_id")
    protected Group group;

    protected Integer wins;
    protected Integer draws;
    protected Integer loses;

    public Member() {initVars();}

    public Member(String nickname, Sports sport, Group group) {
        this.nickname = nickname;
        this.sport = sport;
        this.group = group;
        initVars();
    }

    private void initVars() {
        this.wins = 0;
        this.draws = 0;
        this.loses = 0;
    }

}