package com.vincenzo.bikehub.controller;

import com.vincenzo.bikehub.server.gen.controller.StatsApi;
import com.vincenzo.bikehub.server.gen.model.BicycleCategory;
import com.vincenzo.bikehub.server.gen.model.Stats;
import com.vincenzo.bikehub.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
public class StatsController implements StatsApi {

    private final StatsService statsService;

    protected StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stats> retrieveBicycleStats(UUID bicycleId) {
        return ResponseEntity.ok().body(statsService.retrieveBicycleStats(bicycleId));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stats> retrieveCategoryStats(BicycleCategory categoryType) {
        return ResponseEntity.ok().body(statsService.retrieveCategoryStats(categoryType));
    }
}
