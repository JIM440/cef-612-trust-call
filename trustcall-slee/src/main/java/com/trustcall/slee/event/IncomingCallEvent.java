package com.trustcall.slee.event;

import java.io.Serializable;

public class IncomingCallEvent implements Serializable {

    private final String caller;
    private final String callee;

    public IncomingCallEvent(String caller, String callee) {
        this.caller = caller;
        this.callee = callee;
    }

    public String getCaller() {
        return caller;
    }

    public String getCallee() {
        return callee;
    }
}
