package com.trustcall.repository;

import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FraudReportRepository {

    public void submitReport(String phoneNumber, String reportType) {

        String sql =
                "INSERT INTO fraud_reports " +
                "(reported_number, report_type) VALUES (?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);
            statement.setString(2, reportType);
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println(
                    "Fraud report database error: " + e.getMessage()
            );
        }
    }

    public int countReportsForNumber(String phoneNumber) {

        String sql =
                "SELECT COUNT(*) AS total " +
                "FROM fraud_reports " +
                "WHERE reported_number = ?";

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
            System.out.println(
                    "Fraud count database error: " + e.getMessage()
            );
        }

        return 0;
    }
}
