package com.trustcall.repository;

import com.trustcall.model.CallDecision;
import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CallDecisionRepository {

    public void save(CallDecision decision) {

        String sql =
                "INSERT INTO call_decisions (caller, final_score, action, reason) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, decision.getCaller());
            statement.setInt(2, decision.getFinalScore());
            statement.setString(3, decision.getAction());
            statement.setString(4, decision.getReason());
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
