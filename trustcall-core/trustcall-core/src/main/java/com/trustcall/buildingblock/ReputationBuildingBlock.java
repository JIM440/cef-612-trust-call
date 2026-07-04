package com.trustcall.buildingblock;

import com.trustcall.model.CallerReputation;
import com.trustcall.repository.ReputationRepository;

public class ReputationBuildingBlock {

    private final ReputationRepository reputationRepository =
            new ReputationRepository();

    public int getReputationScore(String phoneNumber) {

        CallerReputation reputation =
                reputationRepository.findByNumber(phoneNumber);

        if (reputation == null) {
            return 50;
        }

        return reputation.getScore();
    }

    public String getReputationStatus(String phoneNumber) {

        CallerReputation reputation =
                reputationRepository.findByNumber(phoneNumber);

        if (reputation == null) {
            return "UNKNOWN";
        }

        return reputation.getStatus();
    }

    public String classifyScore(int score) {

        if (score >= 70) {
            return "GREEN";
        }

        if (score >= 40) {
            return "YELLOW";
        }

        return "RED";
    }
}
