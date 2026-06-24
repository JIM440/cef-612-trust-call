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

        request = request.trim();

        if (request.startsWith("*123*") && request.endsWith("#")) {
            return handleModernReputationLookup(request);
        }

        if (request.startsWith("#123#")) {
            return handleOldReputationLookup(request);
        }

        if (request.startsWith("*55*") && request.endsWith("#")) {
            return handleModernFraudReport(request);
        }

        if (request.startsWith("#55#")) {
            return handleOldFraudReport(request);
        }

        return "Unknown USSD command";
    }

    private String handleModernReputationLookup(String request) {
        String phoneNumber = request
                .replace("*123*", "")
                .replace("#", "")
                .trim();

        return lookupReputation(phoneNumber);
    }

    private String handleOldReputationLookup(String request) {
        String phoneNumber = request
                .replace("#123#", "")
                .trim();

        return lookupReputation(phoneNumber);
    }

    private String lookupReputation(String phoneNumber) {
        CallerReputation reputation =
                reputationRepository.findByNumber(phoneNumber);

        if (reputation == null) {
            return "No reputation found for " + phoneNumber;
        }

        return "Number: " + reputation.getPhoneNumber()
                + " | Score: " + reputation.getScore()
                + " | Status: " + reputation.getStatus();
    }

    private String handleModernFraudReport(String request) {
        String payload = request
                .replace("*55*", "")
                .replace("#", "");

        String[] parts = payload.split("\\*", 2);

        if (parts.length < 2) {
            return "Invalid report format. Use *55*number*reason#";
        }

        return saveFraudReport(parts[0], parts[1]);
    }

    private String handleOldFraudReport(String request) {
        String payload =
                request.replace("#55#", "");

        String[] parts =
                payload.split("#", 2);

        if (parts.length < 2) {
            return "Invalid report format. Use #55#number#reason";
        }

        return saveFraudReport(parts[0], parts[1]);
    }

    private String saveFraudReport(String phoneNumber, String reason) {
        fraudRepository.save(
                new FraudReport(phoneNumber.trim(), reason.trim())
        );

        return "Report submitted for " + phoneNumber.trim();
    }
}
