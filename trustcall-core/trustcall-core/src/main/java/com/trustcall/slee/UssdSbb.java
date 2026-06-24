package com.trustcall.slee;

import com.trustcall.service.UssdService;

/**
 * Simulated USSD Service Building Block.
 */
public class UssdSbb {

    private final UssdService ussdService =
            new UssdService();

    public void onUssdRequest(String request) {

        System.out.println("UssdSbb: USSD event received");

        String response =
                ussdService.processRequest(request);

        System.out.println("UssdSbb: Request  = " + request);
        System.out.println("UssdSbb: Response = " + response);
    }
}
