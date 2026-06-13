package com.trustcall;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import com.trustcall.model.CallerReputation;
import com.trustcall.model.CallDecision;
import com.trustcall.model.SimSwapEvent;
import com.trustcall.repository.ReputationRepository;
import com.trustcall.repository.FraudRepository;
import com.trustcall.repository.WangiriRepository;
import com.trustcall.repository.SimSwapRepository;
import com.trustcall.service.CallAnalysisService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TrustCallHttpServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        server.createContext("/decision", TrustCallHttpServer::handleDecision);

        server.setExecutor(null);

        System.out.println("TrustCall HTTP server running on port 8080");

        server.start();
    }

    private static void handleDecision(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String caller = extractCaller(query);

        String response;

        if (caller == null || caller.trim().isEmpty()) {
            response = "UNKNOWN";
        } else {
            response = getDecision(caller);
        }

        exchange.sendResponseHeaders(200, response.length());

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private static String extractCaller(String query) {

        if (query == null) {
            return null;
        }

        String[] parts = query.split("&");

        for (String part : parts) {
            if (part.startsWith("caller=")) {
                return part.substring("caller=".length());
            }
        }

        return null;
    }

    private static String getDecision(String caller) {

        ReputationRepository reputationRepository =
                new ReputationRepository();

        FraudRepository fraudRepository =
                new FraudRepository();

        WangiriRepository wangiriRepository =
                new WangiriRepository();

        SimSwapRepository simSwapRepository =
                new SimSwapRepository();

        CallAnalysisService analysisService =
                new CallAnalysisService();

        CallerReputation reputation =
                reputationRepository.findByNumber(caller);

        int reputationScore =
                reputation == null ? 50 : reputation.getScore();

        int fraudReports =
                fraudRepository.countReportsForNumber(caller);

        int wangiriEvents =
                wangiriRepository.countEventsForNumber(caller);

        SimSwapEvent simSwapEvent =
                simSwapRepository.findByNumber(caller);

        String simSwapRisk =
                simSwapEvent == null ? "NO SWAP" : "HIGH";

        CallDecision decision =
                analysisService.analyzeCall(
                        caller,
                        reputationScore,
                        fraudReports,
                        wangiriEvents,
                        simSwapRisk
                );

        return decision.getAction();
    }
}
