package com.example.project.model.group;

import java.util.ArrayList;
import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.game.Game;
import com.example.project.model.member.Member;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Group<MemberT extends Member<?>, GameT extends Game<?, ?>> {

    @Id
    @SequenceGenerator(name = "groupSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "groupSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    @JsonIgnoreProperties({ "group", "members" })
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    protected List<GameT> games;

    @JsonIgnoreProperties({ "group", "games" })
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    protected List<MemberT> members;

    public Group() {
        initVars();
    }

    public Group(String name, Sports sport) {
        this.name = name;
        this.sport = sport;
        initVars();
    }

    private void initVars() {
        this.members = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    /* METHODS */

    public void addMember(MemberT member) {
        this.members.add(member);
    }

    public void removeMember(Long memberID) {
        int memberToBeRemoved = -1;
        for (int i = 0; i < this.members.size(); i++) {
            if (this.members.get(i).getId() == memberID) {
                memberToBeRemoved = i;
                break;
            }
        }
        if (memberToBeRemoved != -1) {
            this.members.remove(memberToBeRemoved);
        }
    }

    public void addGame(GameT game) {
        this.games.add(game);
    }

}