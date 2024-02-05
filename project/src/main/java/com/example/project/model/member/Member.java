package com.example.project.model.member;

import java.util.List;

import com.example.project.model.Sports;
import com.example.project.model.User;
import com.example.project.model.game.Game;
import com.example.project.model.group.Group;

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
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public abstract class Member {

    @Id
    @SequenceGenerator(name = "memberSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "memberSeqGen")
    protected Long id;

    @Column(nullable = false)
    protected String nickname;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    protected User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Sports sport;

    public Member(String nickname, Sports sport) {
        this.nickname = nickname;
        this.sport = sport;
    }

}