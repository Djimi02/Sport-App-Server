package com.example.project.request.addNewGameRequests;

import java.util.Map;

import com.example.project.model.stats.Stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AddNewGameRequest<StatsT extends Stats> {
    private Map<Long, StatsT> gameStats; // key is member id and value is member stats
    private Long groupID;
}
