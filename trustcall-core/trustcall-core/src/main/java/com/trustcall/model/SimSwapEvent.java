package com.trustcall.model;

public class SimSwapEvent {

    private String phoneNumber;
    private int daysSinceSwap;

    public SimSwapEvent() {
    }

    public SimSwapEvent(String phoneNumber, int daysSinceSwap) {
        this.phoneNumber = phoneNumber;
        this.daysSinceSwap = daysSinceSwap;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getDaysSinceSwap() {
        return daysSinceSwap;
    }
}
