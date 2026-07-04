package com.trustcall.repository;

import com.trustcall.model.CallDecision;
import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CallDecisionRepository {

    public void save(CallDecision decision) {
        save(decision, "unknown", "trustcall-core");
    }

    public void save(CallDecision decision, String callee, String source) {

        String sql =
                "INSERT INTO call_decisions " +
                "(caller, final_score, action, reason, callee, decision, source) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, decision.getCaller());
            statement.setInt(2, decision.getFinalScore());
            statement.setString(3, decision.getAction());
            statement.setString(4, decision.getReason());
            statement.setString(5, callee == null || callee.trim().isEmpty() ? "unknown" : callee);
            statement.setString(6, decision.getAction());
            statement.setString(7, source == null || source.trim().isEmpty() ? "trustcall-core" : source);

            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Database error while saving call decision: " + e.getMessage());
        }
    }
}
