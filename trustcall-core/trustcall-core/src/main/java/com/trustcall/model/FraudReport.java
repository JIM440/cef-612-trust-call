package com.trustcall.model;

public class FraudReport {

    private String phoneNumber;
    private String reason;

    public FraudReport() {}

    public FraudReport(String phoneNumber, String reason) {
        this.phoneNumber = phoneNumber;
        this.reason = reason;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
