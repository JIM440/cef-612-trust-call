package com.trustcall.repository;

import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SimSwapRepository {

    public void addFlag(
            String phoneNumber,
            String riskLevel,
            String reason
    ) {

        String sql =
                "INSERT INTO sim_flags " +
                "(phone_number, risk_level, reason) " +
                "VALUES (?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);
            statement.setString(2, riskLevel);
            statement.setString(3, reason);
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println(
                    "SIM flag database error: " + e.getMessage()
            );
        }
    }

    public void save(com.trustcall.model.SimSwapEvent event) {
        String riskLevel = event.getDaysSinceSwap() <= 7 ? "HIGH" : "LOW";
        addFlag(event.getPhoneNumber(), riskLevel, "SIM swap event recorded");
    }

    public com.trustcall.model.SimSwapEvent findByNumber(String phoneNumber) {
        String riskLevel = getRiskLevel(phoneNumber);

        if ("LOW".equalsIgnoreCase(riskLevel)) {
            return null;
        }

        return new com.trustcall.model.SimSwapEvent(phoneNumber, 1);
    }

    public String getRiskLevel(String phoneNumber) {

        String sql =
                "SELECT risk_level " +
                "FROM sim_flags " +
                "WHERE phone_number = ? " +
                "ORDER BY created_at DESC " +
                "LIMIT 1";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("risk_level");
            }

        } catch (Exception e) {
            System.out.println(
                    "SIM risk database error: " + e.getMessage()
            );
        }

        return "LOW";
    }
}
