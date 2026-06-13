package com.trustcall.model;

public class User {

    private String phoneNumber;
    private String name;
    private int reputationScore;

    public User() {}

    public User(String phoneNumber, String name, int reputationScore) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.reputationScore = reputationScore;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReputationScore() {
        return reputationScore;
    }

    public void setReputationScore(int reputationScore) {
        this.reputationScore = reputationScore;
    }
}
