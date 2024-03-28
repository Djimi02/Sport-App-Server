package com.example.project.model.game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.group.Group;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Game<GroupT extends Group<?, ?>, MemberT extends Member<?>> {

    @Id
    @SequenceGenerator(name = "gameSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "gameSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected LocalDate date;

    protected String results;
    private Integer victory = null; // -1 -> team 1 won, 0 -> draw, 1 -> team 2 won

    @JsonIgnoreProperties({ "games", "members" })
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupT group;

    @JsonIgnoreProperties({ "group", "user" })
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "football_games_played", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<MemberT> members;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Game() {initVars();}

    public Game(LocalDate date, Sports sport, GroupT group) {
        this.date = date;
        this.sport = sport;
        this.group = group;
        this.victory = null;
        initVars();
    }

    private void initVars() {
        this.members = new ArrayList<>();
    }

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

}