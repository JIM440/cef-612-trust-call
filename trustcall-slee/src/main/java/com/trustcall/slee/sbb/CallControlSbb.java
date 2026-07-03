package com.trustcall.slee.sbb;

import javax.sip.RequestEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipFactory;
import javax.sip.message.MessageFactory;
import javax.sip.message.Response;
import javax.sip.header.FromHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;
import javax.slee.ActivityContextInterface;
import javax.slee.CreateException;
import javax.slee.RolledBackContext;
import javax.slee.Sbb;
import javax.slee.SbbContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class CallControlSbb implements Sbb {

    private SbbContext sbbContext;

    public void onInviteEvent(RequestEvent event, ActivityContextInterface aci) {
        System.out.println("==================================================");
        System.out.println("CallControlSbb: SIP INVITE RECEIVED FROM SipRA");
        System.out.println("==================================================");

        Request request = event.getRequest();

        String caller = extractUserFromFromHeader(request);
        String callee = extractUserFromToHeader(request);

        System.out.println("CallControlSbb: Caller = " + caller);
        System.out.println("CallControlSbb: Callee = " + callee);

        String decision = requestDecision(caller);

        System.out.println("CallControlSbb: TrustCall decision = " + decision);

        if ("BLOCK".equalsIgnoreCase(decision)) {
            System.out.println("CallControlSbb: ACTION = BLOCK CALL - PASSIVE SLEE OBSERVATION ONLY");
        } else if ("WARN".equalsIgnoreCase(decision)) {
            System.out.println("CallControlSbb: ACTION = WARN CALLEE THEN CONTINUE");
        } else {
            System.out.println("CallControlSbb: ACTION = ALLOW CALL");
        }
    }

    public void onIncomingCallEvent(
            com.trustcall.slee.event.IncomingCallEvent event,
            ActivityContextInterface aci) {

        System.out.println("CallControlSbb: legacy IncomingCallEvent received");

        String caller = event.getCaller();
        String callee = event.getCallee();

        System.out.println("CallControlSbb: Caller = " + caller);
        System.out.println("CallControlSbb: Callee = " + callee);

        String decision = requestDecision(caller);

        System.out.println("CallControlSbb: TrustCall decision = " + decision);
    }

    private void rejectCall(RequestEvent event) {
        try {
            ServerTransaction st = event.getServerTransaction();

            if (st == null) {
                System.out.println("CallControlSbb: No ServerTransaction available, cannot reject call");
                return;
            }

            MessageFactory mf = SipFactory.getInstance().createMessageFactory();
            Response response = mf.createResponse(Response.DECLINE, event.getRequest());
            st.sendResponse(response);

            System.out.println("CallControlSbb: Sent SIP 603 DECLINE");
        } catch (Exception e) {
            System.out.println("CallControlSbb: Failed to reject SIP call: " + e.getMessage());
        }
    }

    private String extractUserFromFromHeader(Request request) {
        try {
            FromHeader from = (FromHeader) request.getHeader(FromHeader.NAME);
            String uri = from.getAddress().getURI().toString();
            return uri.replace("sip:", "").split("@")[0];
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private String extractUserFromToHeader(Request request) {
        try {
            ToHeader to = (ToHeader) request.getHeader(ToHeader.NAME);
            String uri = to.getAddress().getURI().toString();
            return uri.replace("sip:", "").split("@")[0];
        } catch (Exception e) {
            return "UNKNOWN";
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

    @Override
    public void setSbbContext(SbbContext context) {
        this.sbbContext = context;
    }

    @Override
    public void unsetSbbContext() {
        this.sbbContext = null;
    }

    @Override
    public void sbbCreate() throws CreateException {}

    @Override
    public void sbbPostCreate() throws CreateException {}

    @Override
    public void sbbActivate() {}

    @Override
    public void sbbPassivate() {}

    @Override
    public void sbbLoad() {}

    @Override
    public void sbbStore() {}

    @Override
    public void sbbRemove() {}

    @Override
    public void sbbExceptionThrown(Exception exception, Object event, ActivityContextInterface aci) {}

    @Override
    public void sbbRolledBack(RolledBackContext context) {}
}
