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
import com.trustcall.service.UssdService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class TrustCallHttpServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081),
                0
        );

        server.createContext("/decision", TrustCallHttpServer::handleDecision);
        server.createContext("/ussd", TrustCallHttpServer::handleUssd);

        server.setExecutor(null);

        System.out.println("TrustCall HTTP server running on port 8081");

        server.start();
    }

    private static void handleDecision(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String caller = getQueryValue(query, "caller");

        String response;

        if (caller == null || caller.trim().isEmpty()) {
            response = "UNKNOWN";
        } else {
            response = getDecision(caller);
        }

        sendResponse(exchange, response);
    }

    private static void handleUssd(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String request = getQueryValue(query, "request");

        UssdService ussdService = new UssdService();

        String response =
                ussdService.processRequest(request);

        sendResponse(exchange, response);
    }

    private static void sendResponse(HttpExchange exchange, String response)
            throws IOException {

        exchange.sendResponseHeaders(200, response.getBytes().length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private static String getQueryValue(String query, String key) {

        if (query == null) {
            return null;
        }

        String[] parts = query.split("&");

        for (String part : parts) {
            if (part.startsWith(key + "=")) {
                try {
                    return URLDecoder.decode(
                            part.substring((key + "=").length()),
                            "UTF-8"
                    );
                } catch (Exception e) {
                    return null;
                }
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
