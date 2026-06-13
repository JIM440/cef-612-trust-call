package com.trustcall.service;

import com.trustcall.model.FraudReport;
import com.trustcall.repository.FraudRepository;

public class FraudService {

    private final FraudRepository repository = new FraudRepository();

    public void reportFraud(String phoneNumber, String reason) {
        FraudReport report = new FraudReport(phoneNumber, reason);
        repository.save(report);
    }

    public int getReportCount(String phoneNumber) {
        return repository.countReportsForNumber(phoneNumber);
    }

    public String getRiskLevel(String phoneNumber) {
        int reports = getReportCount(phoneNumber);

        if (reports >= 3) {
            return "HIGH";
        }

        if (reports >= 1) {
            return "MEDIUM";
        }

        return "LOW";
    }
}
