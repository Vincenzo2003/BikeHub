package com.vincenzo.bikehub.service;

import com.vincenzo.bikehub.models.Bicycle;
import com.vincenzo.bikehub.server.gen.model.BicycleCategory;
import com.vincenzo.bikehub.server.gen.model.Stats;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class StatsService {

    public final BicycleService bicycleService;

    public StatsService(
            BicycleService bicycleService
    ) {
        this.bicycleService = bicycleService;
    }

    public Stats retrieveBicycleStats(UUID bicycleId) {
        Bicycle bicycle = bicycleService.retrieveBicycle(bicycleId, true);
        return bicycle.getStats();
    }

    public Stats retrieveCategoryStats(BicycleCategory categoryType) {
        return bicycleService.retrieveCategoryStats(categoryType);
    }
}
