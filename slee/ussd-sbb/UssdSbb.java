package slee.ussd;

public class UssdSbb {

    public void onUssdRequest(String request) {
        System.out.println("USSD SBB received request: " + request);
    }
}
