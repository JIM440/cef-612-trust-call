package com.trustcall.buildingblock;

import com.trustcall.repository.FraudReportRepository;

public class FraudDetectionBuildingBlock {

    private final FraudReportRepository repository =
            new FraudReportRepository();

    public int countFraudReports(String phoneNumber) {
        return repository.countReportsForNumber(phoneNumber);
    }
}
