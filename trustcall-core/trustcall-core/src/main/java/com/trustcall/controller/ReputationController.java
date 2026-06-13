package com.trustcall.controller;

import com.trustcall.model.CallerReputation;
import com.trustcall.service.ReputationService;

public class ReputationController {

    private final ReputationService reputationService =
            new ReputationService();

    public void displayReputation(String phoneNumber) {

        CallerReputation reputation =
                reputationService.getCallerReputation(phoneNumber);

        if (reputation == null) {
            System.out.println("No reputation found for " + phoneNumber);
            return;
        }

        System.out.println("Phone Number : " + reputation.getPhoneNumber());
        System.out.println("Score        : " + reputation.getScore());
        System.out.println("Status       : " + reputation.getStatus());
    }
}
