package com.trustcall.model;

public class CallerReputation {

    private String phoneNumber;
    private int score;
    private String status;

    public CallerReputation() {
    }

    public CallerReputation(String phoneNumber, int score, String status) {
        this.phoneNumber = phoneNumber;
        this.score = score;
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
