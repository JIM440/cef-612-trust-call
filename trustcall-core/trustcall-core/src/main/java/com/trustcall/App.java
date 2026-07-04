package com.trustcall;

import com.trustcall.controller.CallAnalysisController;

public class App {

    public static void main(String[] args) {

        System.out.println("FINAL TRUSTCALL DECISION");
        System.out.println("----------------");

        CallAnalysisController analysis =
                new CallAnalysisController();

        analysis.displayDecision(
                "650100002",
                3,
                3,
                "HIGH"
        );
    }
}
