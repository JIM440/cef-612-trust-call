package com.trustcall.slee;

public class SleeTestRunner {

    public static void main(String[] args) {

        CallControlSbb callControlSbb =
                new CallControlSbb();

        callControlSbb.onCallReceived("1002", "1001");

        System.out.println();

        UssdSbb ussdSbb =
                new UssdSbb();

        ussdSbb.onUssdRequest("*123#");
    }
}
