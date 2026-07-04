package com.trustcall;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import com.trustcall.buildingblock.FraudDetectionBuildingBlock;
import com.trustcall.buildingblock.SimSwapBuildingBlock;
import com.trustcall.buildingblock.WangiriDetectionBuildingBlock;
import com.trustcall.model.CallDecision;
import com.trustcall.repository.CallDecisionRepository;
import com.trustcall.repository.CallHistoryRepository;
import com.trustcall.repository.FraudReportRepository;
import com.trustcall.service.CallAnalysisService;
import com.trustcall.service.UssdService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

public class TrustCallHttpServer {

    public static void main(String[] args) throws Exception {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/report", TrustCallHttpServer::handleReport);
        server.createContext("/history/view", TrustCallHttpServer::handleHistoryView);
        server.createContext("/history", TrustCallHttpServer::handleHistory);
        server.createContext("/decision/message", TrustCallHttpServer::handleDecisionMessage);
        server.createContext("/decision/details", TrustCallHttpServer::handleDecisionDetails);
        server.createContext("/decision", TrustCallHttpServer::handleDecision);
        server.createContext("/ussd", TrustCallHttpServer::handleUssd);

        server.setExecutor(null);

        System.out.println("TrustCall HTTP server running on port 8081");

        server.start();
    }

    private static void handleReport(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String number = getQueryValue(query, "number");
        String reason = getQueryValue(query, "reason");

        if (number == null || number.trim().isEmpty()) {
            sendJsonResponse(exchange, "{\"error\":\"number is required\"}");
            return;
        }

        if (reason == null || reason.trim().isEmpty()) {
            reason = "Fraud report";
        }

        FraudReportRepository reportRepository =
                new FraudReportRepository();

        reportRepository.submitReport(number, reason);

        int newCount =
                new FraudDetectionBuildingBlock()
                        .countFraudReports(number);

        System.out.println(
                "TRUSTCALL REPORT RECEIVED: number=" + number +
                " reason=" + reason +
                " totalFraudReports=" + newCount
        );

        String response =
                "{\"status\":\"OK\","
                + "\"message\":\"Report submitted\","
                + "\"number\":\"" + safe(number) + "\","
                + "\"fraudReports\":" + newCount
                + "}";

        sendJsonResponse(exchange, response);
    }

    private static void handleHistoryView(HttpExchange exchange)
            throws IOException {

        CallHistoryRepository historyRepository =
                new CallHistoryRepository();

        String response =
                historyRepository.getRecentHistoryHtml(50);

        exchange.getResponseHeaders().add("Content-Type", "text/html");
        sendResponse(exchange, response);
    }

    private static void handleHistory(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String limitValue = getQueryValue(query, "limit");
        String caller = getQueryValue(query, "caller");
        String format = getQueryValue(query, "format");

        int limit = 20;

        try {
            if (limitValue != null && !limitValue.trim().isEmpty()) {
                limit = Integer.parseInt(limitValue);
            }
        } catch (Exception e) {
            limit = 20;
        }

        if (limit <= 0 || limit > 100) {
            limit = 20;
        }

        CallHistoryRepository historyRepository =
                new CallHistoryRepository();

        if ("table".equalsIgnoreCase(format)) {
            sendResponse(
                    exchange,
                    historyRepository.getHistoryTable(caller, limit)
            );
            return;
        }

        sendJsonResponse(
                exchange,
                historyRepository.getHistoryJson(caller, limit)
        );
    }

    private static void handleDecision(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String caller = getQueryValue(query, "caller");
        String callee = getQueryValue(query, "callee");

        if (caller == null || caller.trim().isEmpty()) {
            sendResponse(exchange, "UNKNOWN");
            return;
        }

        sendResponse(exchange, getDecision(caller, callee));
    }

    private static void handleDecisionDetails(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String caller = getQueryValue(query, "caller");
        String callee = getQueryValue(query, "callee");

        if (caller == null || caller.trim().isEmpty()) {
            sendJsonResponse(exchange, "{\"error\":\"caller is required\"}");
            return;
        }

        sendJsonResponse(exchange, getDecisionDetails(caller, callee));
    }

    private static void handleDecisionMessage(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String caller = getQueryValue(query, "caller");
        String callee = getQueryValue(query, "callee");

        if (caller == null || caller.trim().isEmpty()) {
            sendResponse(exchange, "TRUSTCALL: caller is required");
            return;
        }

        sendResponse(exchange, getDecisionMessage(caller, callee));
    }

    private static void handleUssd(HttpExchange exchange)
            throws IOException {

        String query = exchange.getRequestURI().getQuery();
        String request = getQueryValue(query, "request");

        UssdService ussdService =
                new UssdService();

        sendResponse(
                exchange,
                ussdService.processRequest(request)
        );
    }

    private static CallDecision analyze(String caller) {

        FraudDetectionBuildingBlock fraudBlock =
                new FraudDetectionBuildingBlock();

        WangiriDetectionBuildingBlock wangiriBlock =
                new WangiriDetectionBuildingBlock();

        SimSwapBuildingBlock simSwapBlock =
                new SimSwapBuildingBlock();

        int fraudReports =
                fraudBlock.countFraudReports(caller);

        int wangiriEvents =
                wangiriBlock.countWangiriEvents(caller);

        String simSwapRisk =
                simSwapBlock.getSimSwapRisk(caller);

        CallAnalysisService analysisService =
                new CallAnalysisService();

        return analysisService.analyzeCall(
                caller,
                fraudReports,
                wangiriEvents,
                simSwapRisk
        );
    }

    private static String getDecision(String caller, String callee) {

        CallDecision decision =
                analyze(caller);

        CallDecisionRepository decisionRepository =
                new CallDecisionRepository();

        decisionRepository.save(
                decision,
                callee,
                "trustcall-core"
        );

        return decision.getAction();
    }

    private static String getDecisionMessage(
            String caller,
            String callee
    ) {

        FraudDetectionBuildingBlock fraudBlock =
                new FraudDetectionBuildingBlock();

        WangiriDetectionBuildingBlock wangiriBlock =
                new WangiriDetectionBuildingBlock();

        SimSwapBuildingBlock simSwapBlock =
                new SimSwapBuildingBlock();

        int fraudReports =
                fraudBlock.countFraudReports(caller);

        int wangiriEvents =
                wangiriBlock.countWangiriEvents(caller);

        String simSwapRisk =
                simSwapBlock.getSimSwapRisk(caller);

        CallDecision decision =
                analyze(caller);

        String label;

        if ("ALERT".equalsIgnoreCase(decision.getAction())) {
            label = "TRUSTCALL ALERT";
        } else if ("WARN".equalsIgnoreCase(decision.getAction())) {
            label = "TRUSTCALL WARNING";
        } else {
            label = "TRUSTCALL ALLOW";
        }

        String message =
                label +
                ": Caller " + caller +
                " calling " + safePlain(callee) +
                " | Score: " + decision.getFinalScore() +
                " | Fraud reports: " + fraudReports +
                " | Wangiri events: " + wangiriEvents +
                " | SIM risk: " + simSwapRisk +
                " | Decision: " + decision.getAction();

        System.out.println(message);

        return message;
    }

    private static String getDecisionDetails(
            String caller,
            String callee
    ) {

        FraudDetectionBuildingBlock fraudBlock =
                new FraudDetectionBuildingBlock();

        WangiriDetectionBuildingBlock wangiriBlock =
                new WangiriDetectionBuildingBlock();

        SimSwapBuildingBlock simSwapBlock =
                new SimSwapBuildingBlock();

        int fraudReports =
                fraudBlock.countFraudReports(caller);

        int wangiriEvents =
                wangiriBlock.countWangiriEvents(caller);

        String simSwapRisk =
                simSwapBlock.getSimSwapRisk(caller);

        CallDecision decision =
                analyze(caller);

        String reputationStatus;

        if (decision.getFinalScore() >= 70) {
            reputationStatus = "TRUSTED";
        } else if (decision.getFinalScore() >= 40) {
            reputationStatus = "CAUTION";
        } else {
            reputationStatus = "HIGH_RISK";
        }

        return "{"
                + "\"caller\":\"" + safe(caller) + "\","
                + "\"callee\":\"" + safe(callee) + "\","
                + "\"decision\":\"" + safe(decision.getAction()) + "\","
                + "\"finalScore\":" + decision.getFinalScore() + ","
                + "\"reputationStatus\":\"" + reputationStatus + "\","
                + "\"fraudReports\":" + fraudReports + ","
                + "\"wangiriEvents\":" + wangiriEvents + ","
                + "\"simSwapRisk\":\"" + safe(simSwapRisk) + "\","
                + "\"reason\":\"" + safe(decision.getReason()) + "\""
                + "}";
    }

    private static String getQueryValue(String query, String key) {

        if (query == null) {
            return null;
        }

        String[] parts =
                query.split("&");

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

    private static void sendResponse(
            HttpExchange exchange,
            String response
    ) throws IOException {

        byte[] data =
                response.getBytes();

        exchange.sendResponseHeaders(200, data.length);

        OutputStream outputStream =
                exchange.getResponseBody();

        outputStream.write(data);
        outputStream.close();
    }

    private static void sendJsonResponse(
            HttpExchange exchange,
            String response
    ) throws IOException {

        exchange.getResponseHeaders()
                .add("Content-Type", "application/json");

        sendResponse(exchange, response);
    }

    private static String safe(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }

    private static String safePlain(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\n", " ").replace("\r", " ");
    }
}
