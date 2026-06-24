package com.trustcall.slee.sbb;

import com.trustcall.slee.event.UssdRequestEvent;

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
 * USSD Service Building Block.
 */
public abstract class UssdSbb implements Sbb {

    private SbbContext sbbContext;

    public void onUssdRequestEvent(
            UssdRequestEvent event,
            ActivityContextInterface aci) {

        System.out.println("UssdSbb: USSD request event received");

        String response =
                requestUssdResponse(event.getRequest());

        System.out.println("USSD Request : " + event.getRequest());
        System.out.println("USSD Response: " + response);
    }

    private String requestUssdResponse(String request) {

        try {
            URL url = new URL(
                    "http://localhost:8080/ussd?request=" + request
            );

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()
                            )
                    );

            String response = reader.readLine();
            reader.close();

            return response;

        } catch (Exception e) {
            return "USSD SERVICE ERROR";
        }
    }

    public void setSbbContext(SbbContext context) { this.sbbContext = context; }
    public void unsetSbbContext() { this.sbbContext = null; }

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
