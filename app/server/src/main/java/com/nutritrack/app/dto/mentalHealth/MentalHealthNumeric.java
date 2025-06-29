package com.nutritrack.app.dto.mentalHealth;

import lombok.Data;

import java.io.Serializable;

@Data
public class MentalHealthNumeric{
    private boolean omegaImbalance;
    private double totalAddedSugar;
    private double totalDietaryFiber;
    private boolean tryptophanProxy;
    private boolean sodiumHydrationBalance;
    private boolean magnesiumProxy;
}
