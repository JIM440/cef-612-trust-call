package com.trustcall.model;

public class CallRecord {

    private String caller;
    private String receiver;
    private String status;

    public CallRecord() {}

    public CallRecord(String caller, String receiver, String status) {
        this.caller = caller;
        this.receiver = receiver;
        this.status = status;
    }

    public String getCaller() {
        return caller;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
