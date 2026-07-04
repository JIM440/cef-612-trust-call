package com.trustcall.slee.buildingblock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReputationSbbLogic {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/trustcall";

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public int getReputationScore(String phoneNumber) {

        String sql = "SELECT score FROM reputation WHERE number = ?";

        try (
                Connection connection =
                        DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("score");
            }

        } catch (Exception e) {
            System.out.println("ReputationSbbLogic: DB error: " + e.getMessage());
        }

        return 50;
    }

    public String getReputationStatus(String phoneNumber) {

        String sql = "SELECT status FROM reputation WHERE number = ?";

        try (
                Connection connection =
                        DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("status");
            }

        } catch (Exception e) {
            System.out.println("ReputationSbbLogic: DB error: " + e.getMessage());
        }

        return "UNKNOWN";
    }
}
