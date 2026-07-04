package com.trustcall;

import com.trustcall.buildingblock.FraudDetectionBuildingBlock;
import com.trustcall.buildingblock.WangiriDetectionBuildingBlock;
import com.trustcall.buildingblock.SimSwapBuildingBlock;
import com.trustcall.model.CallDecision;
import com.trustcall.service.CallAnalysisService;

public class TrustCallCli {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("UNKNOWN");
            return;
        }

        String caller = args[0];

        FraudDetectionBuildingBlock fraudBlock =
                new FraudDetectionBuildingBlock();

        WangiriDetectionBuildingBlock wangiriBlock =
                new WangiriDetectionBuildingBlock();

        SimSwapBuildingBlock simSwapBlock =
                new SimSwapBuildingBlock();

        int fraudReports =
                fraudBlock.countFraudReports(caller);

        int wangiriEvents =
                wangiriBlock.countWangiriEvents(caller);

        String simSwapRisk =
                simSwapBlock.getSimSwapRisk(caller);

        CallAnalysisService analysisService =
                new CallAnalysisService();

        CallDecision decision =
                analysisService.analyzeCall(
                        caller,
                        fraudReports,
                        wangiriEvents,
                        simSwapRisk
                );

        System.out.println(decision.getAction());
    }
}
