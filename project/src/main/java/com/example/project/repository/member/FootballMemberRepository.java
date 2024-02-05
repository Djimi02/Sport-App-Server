package com.example.project.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.model.member.FootballMember;

@Repository
public interface FootballMemberRepository extends JpaRepository<FootballMember, Long> {
    
}
