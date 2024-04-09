package com.example.project.model.member;



import com.example.project.model.Sports;
import com.example.project.model.User;
import com.example.project.model.group.Group;
import com.example.project.model.stats.Stats;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
public abstract class Member<GroupT extends Group<?, ?>, StatsT extends Stats<?,?>> {

    @Id
    @SequenceGenerator(name = "memberSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "memberSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected String nickname;

    @JsonIgnoreProperties({ "game", "member" })
    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    protected StatsT stats;

    @JsonIgnoreProperties({ "members", "role", "email" })
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    protected User user;

    @JsonIgnoreProperties({ "members", "games" })
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    protected GroupT group;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    protected Boolean isAdmin;

    public Member() {
        initVars();
    }

    public Member(String nickname, Sports sport, GroupT group, StatsT statsT) {
        this.nickname = nickname;
        this.sport = sport;
        this.group = group;
        this.stats = statsT;
        initVars();
    }

    private void initVars() {
        this.isAdmin = false;
    }

}