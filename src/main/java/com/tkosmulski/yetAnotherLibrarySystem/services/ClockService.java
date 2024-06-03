package com.tkosmulski.yetAnotherLibrarySystem.services;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ClockService {
    private Clock clock;

    public ClockService() {
        clock = Clock.system(ZoneId.of("Europe/Warsaw"));
    }

    public Date getCurrentDate() {
        return Date.from(clock.instant());
    }
}
