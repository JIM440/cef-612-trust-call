package com.trustcall.service;

import com.trustcall.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class DemoDataResetService {

    public void resetDemoData() {

        String sql =
                "TRUNCATE TABLE fraud_reports, wangiri_events, simswap_events, call_decisions RESTART IDENTITY";

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Database reset error: " + e.getMessage());
        }
    }
}
