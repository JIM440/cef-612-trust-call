package com.trustcall.slee.buildingblock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WangiriSbbLogic {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/trustcall";

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public int countWangiriEvents(String phoneNumber) {

        String sql = "SELECT COUNT(*) FROM wangiri_events WHERE phone_number = ?";

        try (
                Connection connection =
                        DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("WangiriSbbLogic: DB error: " + e.getMessage());
        }

        return 0;
    }
}
