package com.trustcall.controller;

import com.trustcall.service.FraudService;

public class FraudController {

    private final FraudService fraudService = new FraudService();

    public void submitReport(String phoneNumber, String reason) {
        fraudService.reportFraud(phoneNumber, reason);
        System.out.println("Fraud report submitted for " + phoneNumber);
    }

    public void displayRisk(String phoneNumber) {
        System.out.println("Phone Number : " + phoneNumber);
        System.out.println("Reports      : " + fraudService.getReportCount(phoneNumber));
        System.out.println("Risk Level   : " + fraudService.getRiskLevel(phoneNumber));
    }
}
