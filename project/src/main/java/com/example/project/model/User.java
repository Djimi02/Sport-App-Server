package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

import com.example.project.model.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AppUser")
@Getter
@Setter
@NoArgsConstructor
public class User {
    
    @Id
    @SequenceGenerator(name = "userSeqGen", allocationSize = 1)
    @GeneratedValue(generator = "userSeqGen")
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @JsonIgnoreProperties({"user", "games", "group"})
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private List<Member> members;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email; 
        this.password = password;

        initVars();
    }

    private void initVars() {
        this.members = new ArrayList<>();
        this.role = Roles.USER;
    }

}