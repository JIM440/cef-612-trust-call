package com.trustcall.slee.event;

import java.io.Serializable;

public class UssdRequestEvent implements Serializable {

    private final String request;

    public UssdRequestEvent(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }
}
