package com.trustcall.repository;

import com.trustcall.model.WangiriEvent;
import java.util.ArrayList;
import java.util.List;

public class WangiriRepository {

    private final List<WangiriEvent> events = new ArrayList<>();

    public void save(WangiriEvent event) {
        events.add(event);
    }

    public int countEventsForNumber(String phoneNumber) {
        int count = 0;

        for (WangiriEvent event : events) {
            if (event.getPhoneNumber().equals(phoneNumber)) {
                count++;
            }
        }

        return count;
    }
}
