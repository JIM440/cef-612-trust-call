package com.trustcall.model;

public class WangiriEvent {

    private String phoneNumber;
    private int callDurationSeconds;

    public WangiriEvent() {
    }

    public WangiriEvent(String phoneNumber, int callDurationSeconds) {
        this.phoneNumber = phoneNumber;
        this.callDurationSeconds = callDurationSeconds;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getCallDurationSeconds() {
        return callDurationSeconds;
    }
}
