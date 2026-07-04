package com.trustcall.buildingblock;

import com.trustcall.repository.WangiriRepository;

public class WangiriDetectionBuildingBlock {

    private final WangiriRepository repository =
            new WangiriRepository();

    public int countWangiriEvents(String phoneNumber) {
        return repository.countEventsForNumber(phoneNumber);
    }
}
