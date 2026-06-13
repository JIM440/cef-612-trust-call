package com.trustcall.repository;

import com.trustcall.model.SimSwapEvent;
import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SimSwapRepository {

    public void save(SimSwapEvent event) {

        String sql =
                "INSERT INTO simswap_events (phone_number, days_since_swap) VALUES (?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, event.getPhoneNumber());
            statement.setInt(2, event.getDaysSinceSwap());
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public SimSwapEvent findByNumber(String phoneNumber) {

        String sql =
                "SELECT phone_number, days_since_swap FROM simswap_events WHERE phone_number = ? ORDER BY created_at DESC LIMIT 1";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new SimSwapEvent(
                        resultSet.getString("phone_number"),
                        resultSet.getInt("days_since_swap")
                );
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return null;
    }
}
