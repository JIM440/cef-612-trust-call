package com.trustcall.slee.sbb;

import com.trustcall.slee.event.IncomingCallEvent;

import javax.slee.ActivityContextInterface;
import javax.slee.Sbb;
import javax.slee.SbbContext;
import javax.slee.CreateException;
import javax.slee.RolledBackContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Main Call Control SBB.
 * Entry point for incoming call events.
 */
public abstract class CallControlSbb implements Sbb {

    private SbbContext sbbContext;

    public void onIncomingCallEvent(
            IncomingCallEvent event,
            ActivityContextInterface aci) {

        String caller = event.getCaller();
        String callee = event.getCallee();

        System.out.println("CallControlSbb: Incoming call event received");
        System.out.println("Caller: " + caller);
        System.out.println("Callee: " + callee);

        String decision = requestDecision(caller);

        System.out.println("CallControlSbb: TrustCall decision = " + decision);

        if ("BLOCK".equalsIgnoreCase(decision)) {
            System.out.println("CallControlSbb: ACTION = BLOCK CALL");
        } else if ("WARN".equalsIgnoreCase(decision)) {
            System.out.println("CallControlSbb: ACTION = WARN CALLEE THEN CONTINUE");
        } else {
            System.out.println("CallControlSbb: ACTION = ALLOW CALL");
        }
    }

    private String requestDecision(String caller) {
        try {
            URL url = new URL("http://localhost:8081/decision?caller=" + caller);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String response = reader.readLine();
            reader.close();

            if (response == null || response.trim().isEmpty()) {
                return "UNKNOWN";
            }

            return response.trim();

        } catch (Exception e) {
            System.out.println("CallControlSbb: Failed to contact TrustCall Core: " + e.getMessage());
            return "UNKNOWN";
        }
    }

    public void setSbbContext(SbbContext context) {
        this.sbbContext = context;
    }

    public void unsetSbbContext() {
        this.sbbContext = null;
    }

    public void sbbCreate() throws CreateException {}
    public void sbbPostCreate() throws CreateException {}
    public void sbbActivate() {}
    public void sbbPassivate() {}
    public void sbbLoad() {}
    public void sbbStore() {}
    public void sbbRemove() {}
    public void sbbExceptionThrown(Exception exception, Object event, ActivityContextInterface aci) {}
    public void sbbRolledBack(RolledBackContext context) {}
}
