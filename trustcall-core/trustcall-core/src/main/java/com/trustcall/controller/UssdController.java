package com.trustcall.controller;

import com.trustcall.service.UssdService;

public class UssdController {

    private final UssdService ussdService =
            new UssdService();

    public void process(String request) {

        String response =
                ussdService.processRequest(request);

        System.out.println("USSD Request : " + request);
        System.out.println("USSD Response: " + response);
    }
}
