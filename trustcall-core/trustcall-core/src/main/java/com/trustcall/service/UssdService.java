package com.trustcall.service;

import com.trustcall.model.CallerReputation;
import com.trustcall.repository.ReputationRepository;
import com.trustcall.repository.FraudRepository;
import com.trustcall.model.FraudReport;

public class UssdService {

    private final ReputationRepository reputationRepository =
            new ReputationRepository();

    private final FraudRepository fraudRepository =
            new FraudRepository();

    public String processRequest(String request) {

        if (request == null || request.trim().isEmpty()) {
            return "Invalid USSD request";
        }

        if (request.startsWith("#123#")) {
            return handleReputationLookup(request);
        }

        if (request.startsWith("#55#")) {
            return handleFraudReport(request);
        }

        return "Unknown USSD command";
    }

    private String handleReputationLookup(String request) {

        String phoneNumber =
                request.replace("#123#", "").trim();

        CallerReputation reputation =
                reputationRepository.findByNumber(phoneNumber);

        if (reputation == null) {
            return "No reputation found for " + phoneNumber;
        }

        return "Number: " + reputation.getPhoneNumber()
                + " | Score: " + reputation.getScore()
                + " | Status: " + reputation.getStatus();
    }

    private String handleFraudReport(String request) {

        String payload =
                request.replace("#55#", "");

        String[] parts =
                payload.split("#", 2);

        if (parts.length < 2) {
            return "Invalid report format. Use #55#number#reason";
        }

        String phoneNumber =
                parts[0].trim();

        String reason =
                parts[1].trim();

        fraudRepository.save(
                new FraudReport(phoneNumber, reason)
        );

        return "Report submitted for " + phoneNumber;
    }
}
