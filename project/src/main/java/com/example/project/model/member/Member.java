package com.example.project.model.member;

import com.example.project.model.MemberRole;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Member {

    @Id
    @SequenceGenerator(name = "memberSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "memberSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected String nickname;

    @JsonIgnoreProperties({ "members", "role", "email" })
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    protected User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    @Enumerated(EnumType.STRING)
    protected MemberRole role;

    // @JsonIgnoreProperties({ "members", "games" })
    // @Transient
    // protected Group groupAbs;

    // @JsonIgnoreProperties({ "game" })
    // @Transient
    // protected Stats statsAbs;

    public Member() {}

}