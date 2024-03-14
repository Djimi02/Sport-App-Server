package com.example.project.request;


import java.util.List;

import com.example.project.model.member.FootballMember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewFootballGameRequest {
    private List<FootballMember> updatedMembers;
    private List<FootballMember> membersGameStats;
    private Long groupID;
    private Integer victory; // -1 -> team 1 won, 0 -> draw, 1 -> team 2 won
}