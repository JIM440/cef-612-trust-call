package com.trustcall.repository;

import com.trustcall.model.WangiriEvent;
import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WangiriRepository {

    public void save(WangiriEvent event) {

        String sql =
                "INSERT INTO wangiri_events (phone_number, call_duration_seconds) VALUES (?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, event.getPhoneNumber());
            statement.setInt(2, event.getCallDurationSeconds());
            statement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public int countEventsForNumber(String phoneNumber) {

        String sql =
                "SELECT COUNT(*) AS total FROM wangiri_events WHERE phone_number = ?";

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
