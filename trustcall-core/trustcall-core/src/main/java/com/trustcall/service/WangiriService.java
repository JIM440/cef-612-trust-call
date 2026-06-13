package com.trustcall.service;

import com.trustcall.model.WangiriEvent;
import com.trustcall.repository.WangiriRepository;

public class WangiriService {

    private final WangiriRepository repository = new WangiriRepository();

    public boolean isShortCall(int durationSeconds) {
        return durationSeconds <= 5;
    }

    public void recordCall(String phoneNumber, int durationSeconds) {
        if (isShortCall(durationSeconds)) {
            repository.save(new WangiriEvent(phoneNumber, durationSeconds));
        }
    }

    public int getWangiriEventCount(String phoneNumber) {
        return repository.countEventsForNumber(phoneNumber);
    }

    public String detectWangiri(String phoneNumber) {
        int count = getWangiriEventCount(phoneNumber);

        if (count >= 3) {
            return "WANGIRI DETECTED";
        }

        return "NORMAL";
    }
}
