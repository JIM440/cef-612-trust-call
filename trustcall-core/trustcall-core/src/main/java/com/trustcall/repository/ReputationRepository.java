package com.trustcall.repository;

import com.trustcall.model.CallerReputation;
import java.util.HashMap;
import java.util.Map;

public class ReputationRepository {

    private final Map<String, CallerReputation> database = new HashMap<>();

    public ReputationRepository() {
        database.put("1001",
                new CallerReputation("1001", 95, "Trusted"));

        database.put("1002",
                new CallerReputation("1002", 15, "Suspected Spam"));
    }

    public CallerReputation findByNumber(String phoneNumber) {
        return database.get(phoneNumber);
    }
}
