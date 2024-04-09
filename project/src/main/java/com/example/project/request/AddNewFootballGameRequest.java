package com.example.project.request;

import java.util.Map;

import com.example.project.model.stats.FBStats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewFootballGameRequest {
    private Map<Long, FBStats> gameStats; // key is member id and value is member stats
    private Long groupID;
    private Integer victory; // -1 -> team 1 won, 0 -> draw, 1 -> team 2 won
}