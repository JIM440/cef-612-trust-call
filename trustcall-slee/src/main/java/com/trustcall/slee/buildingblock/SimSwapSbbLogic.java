package com.trustcall.slee.buildingblock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SimSwapSbbLogic {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/trustcall";

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public String getSimSwapRisk(String phoneNumber) {

        String sql =
                "SELECT days_since_swap FROM simswap_events WHERE phone_number = ? " +
                "ORDER BY created_at DESC LIMIT 1";

        try (
                Connection connection =
                        DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int days = resultSet.getInt("days_since_swap");

                if (days <= 3) {
                    return "HIGH";
                }

                if (days <= 7) {
                    return "MEDIUM";
                }

                return "LOW";
            }

        } catch (Exception e) {
            System.out.println("SimSwapSbbLogic: DB error: " + e.getMessage());
        }

        return "NO SWAP";
    }
}
