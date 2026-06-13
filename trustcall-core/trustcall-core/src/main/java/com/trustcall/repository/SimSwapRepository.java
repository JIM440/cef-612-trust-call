package com.trustcall.repository;

import com.trustcall.model.SimSwapEvent;
import java.util.HashMap;
import java.util.Map;

public class SimSwapRepository {

    private final Map<String, SimSwapEvent> swaps =
            new HashMap<>();

    public void save(SimSwapEvent event) {
        swaps.put(event.getPhoneNumber(), event);
    }

    public SimSwapEvent findByNumber(String phoneNumber) {
        return swaps.get(phoneNumber);
    }
}
