package com.trustcall.repository;

import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CallHistoryRepository {

    public String getRecentHistoryJson(int limit) {
        return getHistoryJson(null, limit);
    }

    public String getHistoryJson(String caller, int limit) {

        String sql;

        if (caller == null || caller.trim().isEmpty()) {
            sql = "SELECT id, caller, callee, decision, reason, final_score, source, created_at " +
                    "FROM call_decisions ORDER BY created_at DESC LIMIT ?";
        } else {
            sql = "SELECT id, caller, callee, decision, reason, final_score, source, created_at " +
                    "FROM call_decisions WHERE caller = ? ORDER BY created_at DESC LIMIT ?";
        }

        StringBuilder json = new StringBuilder();
        json.append("[");

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            if (caller == null || caller.trim().isEmpty()) {
                statement.setInt(1, limit);
            } else {
                statement.setString(1, caller);
                statement.setInt(2, limit);
            }

            ResultSet resultSet = statement.executeQuery();

            boolean first = true;

            while (resultSet.next()) {
                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":").append(resultSet.getInt("id")).append(",")
                        .append("\"caller\":\"").append(safe(resultSet.getString("caller"))).append("\",")
                        .append("\"callee\":\"").append(safe(resultSet.getString("callee"))).append("\",")
                        .append("\"decision\":\"").append(safe(resultSet.getString("decision"))).append("\",")
                        .append("\"finalScore\":").append(resultSet.getInt("final_score")).append(",")
                        .append("\"reason\":\"").append(safe(resultSet.getString("reason"))).append("\",")
                        .append("\"source\":\"").append(safe(resultSet.getString("source"))).append("\",")
                        .append("\"createdAt\":\"").append(resultSet.getTimestamp("created_at")).append("\"")
                        .append("}");

                first = false;
            }

        } catch (Exception e) {
            return "{\"error\":\"Failed to load call history: " + safe(e.getMessage()) + "\"}";
        }

        json.append("]");
        return json.toString();
    }

    public String getHistoryTable(String caller, int limit) {

        String sql;

        if (caller == null || caller.trim().isEmpty()) {
            sql = "SELECT id, caller, callee, decision, reason, final_score, source, created_at " +
                    "FROM call_decisions ORDER BY created_at DESC LIMIT ?";
        } else {
            sql = "SELECT id, caller, callee, decision, reason, final_score, source, created_at " +
                    "FROM call_decisions WHERE caller = ? ORDER BY created_at DESC LIMIT ?";
        }

        StringBuilder table = new StringBuilder();

        table.append(String.format("%-5s %-12s %-12s %-8s %-6s %-20s %-25s%n",
                "ID", "CALLER", "CALLEE", "ACTION", "SCORE", "SOURCE", "TIME"));
        table.append("------------------------------------------------------------------------------------------\n");

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            if (caller == null || caller.trim().isEmpty()) {
                statement.setInt(1, limit);
            } else {
                statement.setString(1, caller);
                statement.setInt(2, limit);
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                table.append(String.format("%-5d %-12s %-12s %-8s %-6d %-20s %-25s%n",
                        resultSet.getInt("id"),
                        safe(resultSet.getString("caller")),
                        safe(resultSet.getString("callee")),
                        safe(resultSet.getString("decision")),
                        resultSet.getInt("final_score"),
                        safe(resultSet.getString("source")),
                        String.valueOf(resultSet.getTimestamp("created_at"))
                ));
            }

        } catch (Exception e) {
            table.append("Failed to load call history: ").append(safe(e.getMessage()));
        }

        return table.toString();
    }

    public String getRecentHistoryHtml(int limit) {
        return "<html><body><pre>" + getHistoryTable(null, limit) + "</pre></body></html>";
    }

    private String safe(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
