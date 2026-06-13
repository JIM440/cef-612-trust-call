package com.trustcall.repository;

import com.trustcall.model.FraudReport;
import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FraudRepository {

    public void save(FraudReport report) {

        String sql =
                "INSERT INTO fraud_reports (phone_number, reason) VALUES (?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, report.getPhoneNumber());
            statement.setString(2, report.getReason());
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public int countReportsForNumber(String phoneNumber) {

        String sql =
                "SELECT COUNT(*) AS total FROM fraud_reports WHERE phone_number = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return 0;
    }
}
