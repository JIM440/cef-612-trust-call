package slee.fraud;

public class FraudSbb {

    public void onFraudEvent(String phoneNumber) {
        System.out.println("Fraud SBB received fraud event for: " + phoneNumber);
    }
}
