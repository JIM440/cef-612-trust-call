package com.trustcall.service;

import com.trustcall.model.SimSwapEvent;
import com.trustcall.repository.SimSwapRepository;

public class SimSwapService {

    private final SimSwapRepository repository =
            new SimSwapRepository();

    public void registerSwap(String phoneNumber,
                             int daysSinceSwap) {

        repository.save(
                new SimSwapEvent(
                        phoneNumber,
                        daysSinceSwap
                )
        );
    }

    public String getRiskLevel(String phoneNumber) {

        SimSwapEvent event =
                repository.findByNumber(phoneNumber);

        if (event == null) {
            return "NO SWAP";
        }

        if (event.getDaysSinceSwap() <= 7) {
            return "HIGH";
        }

        if (event.getDaysSinceSwap() <= 30) {
            return "MEDIUM";
        }

        return "LOW";
    }
}
