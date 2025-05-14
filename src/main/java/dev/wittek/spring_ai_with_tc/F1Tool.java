package dev.wittek.spring_ai_with_tc;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.web.client.RestClient;

public class F1Tool {

    private RestClient ergastClient;

    public F1Tool() {
        this.ergastClient = RestClient.builder()
                .baseUrl("https://api.jolpi.ca/ergast/f1/")
                .build();
    }

    @Tool(description = "Get results for the most recent F1 race of this season.")
    String getResultsForLastRace() {
        String results = ergastClient.get()
                .uri("/current/last/results.json")
                .retrieve().body(String.class);
        return results;
    }

}