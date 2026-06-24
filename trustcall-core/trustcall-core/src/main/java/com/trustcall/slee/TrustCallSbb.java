package com.trustcall.slee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Simulated RestComm JAIN SLEE Service Building Block.
 */
public class TrustCallSbb {

    public void onIncomingCall(String caller, String callee) {

        System.out.println("SLEE Event: Incoming call detected");
        System.out.println("Caller: " + caller);
        System.out.println("Callee: " + callee);

        String decision =
                requestDecisionFromTrustCallCore(caller);

        System.out.println("TrustCall Decision: " + decision);

        if ("BLOCK".equalsIgnoreCase(decision)) {
            blockCall(caller, callee);
        } else if ("WARN".equalsIgnoreCase(decision)) {
            warnUser(caller, callee);
        } else {
            allowCall(caller, callee);
        }
    }

    private String requestDecisionFromTrustCallCore(String caller) {

        try {
            URL url = new URL(
                    "http://localhost:8080/decision?caller=" + caller
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
            return "UNKNOWN";
        }
    }

    private void allowCall(String caller, String callee) {
        System.out.println("SLEE Action: Call allowed");
    }

    private void warnUser(String caller, String callee) {
        System.out.println("SLEE Action: Warning shown before call");
    }

    private void blockCall(String caller, String callee) {
        System.out.println("SLEE Action: Call blocked");
    }
}
