package slee.events;

public class IncomingCallEvent {

    private String caller;
    private String receiver;

    public IncomingCallEvent(String caller, String receiver) {
        this.caller = caller;
        this.receiver = receiver;
    }

    public String getCaller() {
        return caller;
    }

    public String getReceiver() {
        return receiver;
    }
}
