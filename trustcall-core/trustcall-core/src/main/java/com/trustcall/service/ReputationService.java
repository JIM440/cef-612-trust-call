package com.trustcall.service;

import com.trustcall.model.CallerReputation;
import com.trustcall.repository.ReputationRepository;

public class ReputationService {

    private final ReputationRepository repository =
            new ReputationRepository();

    public CallerReputation getCallerReputation(String phoneNumber) {
        return repository.findByNumber(phoneNumber);
    }
}
