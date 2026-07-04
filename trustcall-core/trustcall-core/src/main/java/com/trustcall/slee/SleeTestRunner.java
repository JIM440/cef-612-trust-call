package com.trustcall.slee;

public class SleeTestRunner {

    public static void main(String[] args) {

        CallControlSbb callControlSbb =
                new CallControlSbb();

        callControlSbb.onCallReceived("650100002", "650100001");

        System.out.println();

        UssdSbb ussdSbb =
                new UssdSbb();

        ussdSbb.onUssdRequest("*123#");
    }
}
