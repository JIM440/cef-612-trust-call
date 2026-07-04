package com.trustcall.buildingblock;

import com.trustcall.repository.SimSwapRepository;

public class SimSwapBuildingBlock {

    private final SimSwapRepository repository =
            new SimSwapRepository();

    public String getSimSwapRisk(String phoneNumber) {
        return repository.getRiskLevel(phoneNumber);
    }
}
