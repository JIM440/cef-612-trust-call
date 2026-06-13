package com.trustcall.model;

public class CallDecision {

    private String caller;
    private int finalScore;
    private String action;
    private String reason;

    public CallDecision(String caller, int finalScore, String action, String reason) {
        this.caller = caller;
        this.finalScore = finalScore;
        this.action = action;
        this.reason = reason;
    }

    public String getCaller() {
        return caller;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public String getAction() {
        return action;
    }

    public String getReason() {
        return reason;
    }
}
