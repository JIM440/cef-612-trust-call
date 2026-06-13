package com.trustcall.repository;

import com.trustcall.model.CallerReputation;
import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReputationRepository {

    public CallerReputation findByNumber(String phoneNumber) {

        String sql =
                "SELECT number, score, status FROM reputation WHERE number = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {
                return new CallerReputation(
                        resultSet.getString("number"),
                        resultSet.getInt("score"),
                        resultSet.getString("status")
                );
            }

        } catch (Exception e) {
            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }

        return null;
    }
}
