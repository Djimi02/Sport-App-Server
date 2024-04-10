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
}