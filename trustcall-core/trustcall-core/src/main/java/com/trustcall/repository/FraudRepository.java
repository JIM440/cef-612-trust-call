package com.trustcall.repository;

import com.trustcall.model.FraudReport;
import java.util.ArrayList;
import java.util.List;

public class FraudRepository {

    private final List<FraudReport> reports = new ArrayList<>();

    public void save(FraudReport report) {
        reports.add(report);
    }

    public List<FraudReport> findAll() {
        return reports;
    }

    public int countReportsForNumber(String phoneNumber) {
        int count = 0;

        for (FraudReport report : reports) {
            if (report.getPhoneNumber().equals(phoneNumber)) {
                count++;
            }
        }

        return count;
    }
}
