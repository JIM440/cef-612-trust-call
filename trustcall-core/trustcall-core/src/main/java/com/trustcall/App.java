package com.trustcall;

import com.trustcall.controller.ReputationController;
import com.trustcall.controller.FraudController;
import com.trustcall.controller.WangiriController;
import com.trustcall.controller.SimSwapController;
import com.trustcall.controller.CallAnalysisController;
import com.trustcall.service.DemoDataResetService;

public class App {

    public static void main(String[] args) {

        DemoDataResetService resetService = new DemoDataResetService();
        resetService.resetDemoData();

        System.out.println("=================================");
        System.out.println("      TRUSTCALL DEMO SYSTEM");
        System.out.println("=================================");

        System.out.println();
        System.out.println("REPUTATION CHECK");
        System.out.println("----------------");

        ReputationController reputation = new ReputationController();
        reputation.displayReputation("1001");
        System.out.println();
        reputation.displayReputation("1002");

        System.out.println();
        System.out.println("FRAUD REPORTING");
        System.out.println("----------------");

        FraudController fraud = new FraudController();
        fraud.submitReport("1002", "Mobile Money Scam");
        fraud.submitReport("1002", "Fake Promotion");
        fraud.submitReport("1002", "Identity Theft");
        fraud.displayRisk("1002");

        System.out.println();
        System.out.println("WANGIRI DETECTION");
        System.out.println("----------------");

        WangiriController wangiri = new WangiriController();
        wangiri.recordCall("1002", 2);
        wangiri.recordCall("1002", 3);
        wangiri.recordCall("1002", 4);
        wangiri.displayWangiriStatus("1002");

        System.out.println();
        System.out.println("SIM SWAP CHECK");
        System.out.println("----------------");

        SimSwapController simSwap = new SimSwapController();
        simSwap.registerSwap("1002", 2);
        simSwap.displayRisk("1002");

        System.out.println();
        System.out.println("FINAL TRUSTCALL DECISION");
        System.out.println("----------------");

        CallAnalysisController analysis = new CallAnalysisController();

        analysis.displayDecision(
                "1002",
                15,
                3,
                3,
                "HIGH"
        );
    }
}
