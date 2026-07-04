package com.trustcall.service;

import com.trustcall.buildingblock.FraudDetectionBuildingBlock;
import com.trustcall.buildingblock.WangiriDetectionBuildingBlock;
import com.trustcall.buildingblock.SimSwapBuildingBlock;
import com.trustcall.model.CallDecision;
import com.trustcall.repository.FraudReportRepository;

public class UssdService {

    public String processRequest(String request) {

        if (request == null || request.trim().isEmpty()) {
            return "Invalid USSD request";
        }

        request = request.trim();

        if (request.startsWith("*123*") && request.endsWith("#")) {
            String number = request
                    .replace("*123*", "")
                    .replace("#", "");

            return lookupNumber(number);
        }

        if (request.startsWith("*55*") && request.endsWith("#")) {
            String body = request
                    .replace("*55*", "")
                    .replace("#", "");

            String[] parts = body.split("\\*", 2);

            if (parts.length < 1 || parts[0].trim().isEmpty()) {
                return "Invalid fraud report format";
            }

            String number = parts[0].trim();
            String reason = parts.length > 1 ? parts[1].trim() : "Fraud report";

            FraudReportRepository repository =
                    new FraudReportRepository();

            repository.submitReport(number, reason);

            System.out.println(
                    "USSD FRAUD REPORT: number=" + number +
                    " reason=" + reason
            );

            return "Report submitted for " + number;
        }

        return "Unknown USSD command. Use *123*number# or *55*number*reason#";
    }

    private String lookupNumber(String number) {

        FraudDetectionBuildingBlock fraudBlock =
                new FraudDetectionBuildingBlock();

        WangiriDetectionBuildingBlock wangiriBlock =
                new WangiriDetectionBuildingBlock();

        SimSwapBuildingBlock simSwapBlock =
                new SimSwapBuildingBlock();

        int fraudReports =
                fraudBlock.countFraudReports(number);

        int wangiriEvents =
                wangiriBlock.countWangiriEvents(number);

        String simSwapRisk =
                simSwapBlock.getSimSwapRisk(number);

        CallAnalysisService analysisService =
                new CallAnalysisService();

        CallDecision decision =
                analysisService.analyzeCall(
                        number,
                        fraudReports,
                        wangiriEvents,
                        simSwapRisk
                );

        return "Number: " + number +
                "\\nDecision: " + decision.getAction() +
                "\\nScore: " + decision.getFinalScore() +
                "\\nFraud reports: " + fraudReports +
                "\\nWangiri events: " + wangiriEvents +
                "\\nSIM risk: " + simSwapRisk;
    }
}
