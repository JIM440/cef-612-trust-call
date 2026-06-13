package com.trustcall.controller;

import com.trustcall.service.SimSwapService;

public class SimSwapController {

    private final SimSwapService service =
            new SimSwapService();

    public void registerSwap(String number,
                             int daysSinceSwap) {

        service.registerSwap(
                number,
                daysSinceSwap
        );
    }

    public void displayRisk(String number) {

        System.out.println(
                "Phone Number : " + number
        );

        System.out.println(
                "SIM Swap Risk: "
                        + service.getRiskLevel(number)
        );
    }
}
