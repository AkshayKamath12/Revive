package com.nutritrack.app.service.insights;

import com.nutritrack.app.dto.SleepInsightsDTO;
import com.nutritrack.app.dto.sleep.SleepNumeric;
import com.nutritrack.app.dto.sleep.SleepScores;
import com.nutritrack.app.dto.sleep.SleepText;
import com.nutritrack.app.entity.Meal;
import com.nutritrack.app.entity.NutriLabel;
import com.nutritrack.app.service.NutriLabelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepInsightsService {
    public SleepInsightsDTO getSleepInsights(Meal meal) {
        SleepInsightsDTO sleepInsights = new SleepInsightsDTO();
        List<NutriLabel> nutriLabels = meal.getNutritionLabels();
        double totalAddedSugars = 0;
        double totalSodium = 0;
        double totalDietaryFiber = 0;
        double totalProtein = 0;

        for (NutriLabel nutriLabel : nutriLabels) {
            totalAddedSugars += nutriLabel.getAddedSugars();
            totalSodium += nutriLabel.getSodium();
            totalDietaryFiber += nutriLabel.getDietaryFiber();
            totalProtein += nutriLabel.getProtein();
        }

        double nutriLabelSize = nutriLabels.size();
        double averageAddedSugars = totalAddedSugars / nutriLabelSize;
        double averageSodium = totalSodium / nutriLabelSize;
        double averageDietaryFiber = totalDietaryFiber / nutriLabelSize;
        double averageProtein = totalProtein / nutriLabelSize;

        SleepNumeric numericInsights = new SleepNumeric();
        SleepText textInsights = new SleepText();
        SleepScores sleepScores = new SleepScores();

        //handle average added sugars
        numericInsights.setAverageAddedSugars(averageAddedSugars);
        textInsights.setAverageAddedSugarsInsight(sugarInsights(averageAddedSugars, sleepScores));

        //handle average sodium
        numericInsights.setAverageSodium(averageSodium);
        textInsights.setAverageSodiumInsight(sodiumInsights(averageSodium, sleepScores));

        //handle average dietary fiber
        numericInsights.setAverageDietaryFiber(averageDietaryFiber);
        textInsights.setAverageDietaryFiberInsight(dietaryFiberInsights(averageDietaryFiber, sleepScores));

        //handle average protein
        numericInsights.setAverageProtein(averageProtein);
        textInsights.setAverageProteinInsight(proteinInsights(averageProtein, sleepScores));

        sleepInsights.setSleepNumeric(numericInsights);
        sleepInsights.setSleepText(textInsights);
        sleepInsights.setSleepScores(sleepScores);

        return sleepInsights;
    }

    private String sugarInsights(double averageAddedSugars, SleepScores sleepScores) {
        String result = "The average added sugars is " + averageAddedSugars + " grams.\n";
        if(averageAddedSugars > 15) {
            sleepScores.setAverageAddedSugarsScore(1);
            return result + "This is higher than you would want and research links high sugar consumption to a higher frequency of waking up at night.";
        } else if(averageAddedSugars > 5) {
            sleepScores.setAverageAddedSugarsScore(5);
            return result + "This is about average for a given meal. Consider slightly reducing added sugar later in the day to help with sleep.";
        } else {
            sleepScores.setAverageAddedSugarsScore(10);
            return result + "You're doing a great job at limiting products with high added sugars. Make sure to keep doing so.";
        }
    }

    private String sodiumInsights(double averageAddedSodium, SleepScores sleepScores) {
        String result = "The average sodium is " + averageAddedSodium + " grams.\n";
        if(averageAddedSodium > 800) {
            sleepScores.setAverageSodiumScore(1);
            return result + "Your sodium intake is high. High sodium is linked to increased nighttime urination and reduced deep sleep due to elevated blood pressure. Consider lighter, low-salt evening meals to minimize sleep disruptions.";
        } else if(averageAddedSodium > 500) {
            sleepScores.setAverageSodiumScore(5);
            return result + "Your sodium levels are moderate. While within a healthy range, avoiding sodium at dinner time helps reduce overnight water retention and supports steady cardiovascular rhythms during sleep.";
        } else {
            sleepScores.setAverageSodiumScore(10);
            return result + "Your sodium intake per meal is low. This supports the body’s natural nighttime drop in blood pressure, which is essential for deep, uninterrupted sleep.";
        }
    }

    private String dietaryFiberInsights(double averageDietaryFiber, SleepScores sleepScores) {
        String result = "The average dietary fiber is " + averageDietaryFiber + " grams.\n";
        if(averageDietaryFiber < 5) {
            sleepScores.setAverageDietaryFiberScore(1);
            return result + "Your meals are low in dietary fiber. Low fiber is linked to reduced time in slow-wave sleep, which is the stage your brain uses for memory consolidation and physical restoration.";
        } else if(averageDietaryFiber < 8) {
            sleepScores.setAverageDietaryFiberScore(5);
            return result + "You’re getting a fair amount of fiber. Research shows moderate fiber intake helps prevent blood sugar crashes at night, supporting more continuous sleep.";
        } else {
            sleepScores.setAverageDietaryFiberScore(10);
            return result + "Excellent, your fiber intake is high. Higher fiber diets are linked to more time spent in deep sleep and shorter time to fall asleep, helping your body and brain recover overnight.";
        }
    }

    private String proteinInsights(double averageProtein, SleepScores sleepScores) {
        String result = "The average protein is " + averageProtein + " grams.\n";
        if(averageProtein < 10) {
            sleepScores.setAverageProteinScore(5);
            return result + "Your protein intake is on the lower side. Low protein can limit the production of serotonin and melatonin, which may make it harder to fall asleep or stay asleep through the night.";
        } else if(averageProtein < 25) {
            sleepScores.setAverageProteinScore(10);
            return result + "Your protein intake per meal is in a healthy range. Moderate protein supports stable blood sugar and promotes melatonin production, which can help you fall asleep more easily and sleep more deeply.";
        } else {
            sleepScores.setAverageProteinScore(5);
            return result + "This meal is high in protein. While protein is essential, very high amounts, especially close to bedtime, can increase alertness and reduce REM sleep";
        }
    }
}
