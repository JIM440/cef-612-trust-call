package com.trustcall.controller;

import com.trustcall.service.WangiriService;

public class WangiriController {

    private final WangiriService wangiriService = new WangiriService();

    public void recordCall(String phoneNumber, int durationSeconds) {
        wangiriService.recordCall(phoneNumber, durationSeconds);
    }

    public void displayWangiriStatus(String phoneNumber) {
        System.out.println("Phone Number   : " + phoneNumber);
        System.out.println("Short Calls    : " + wangiriService.getWangiriEventCount(phoneNumber));
        System.out.println("Wangiri Status : " + wangiriService.detectWangiri(phoneNumber));
    }
}
