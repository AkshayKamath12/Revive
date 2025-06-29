package com.nutritrack.app.dto.weight;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeightScores {
    private double totalCaloriesScores;
    private double averageCaloriesScores;
    private double totalAddedSugarsScores;
    private double exceedSodiumCarbLimitScores;
    private double satietyScoreScores;
}
