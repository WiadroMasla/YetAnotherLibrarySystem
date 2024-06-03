package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.services.serviceInterfaces.CurrentDateProvider;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZoneId;
import java.util.Date;

@Service(value = "clockService")
public class ClockService implements CurrentDateProvider {
    private Clock clock;

    public ClockService() {
        clock = Clock.system(ZoneId.of("Europe/Warsaw"));
    }

    public Date getCurrentDate() {
        return Date.from(clock.instant());
    }
}
