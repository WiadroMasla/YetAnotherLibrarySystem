package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.services.serviceInterfaces.CurrentDateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZoneId;
import java.util.Date;

@Service(value = "clockService")
public class ClockService implements CurrentDateProvider {
    private Clock clock;
    Logger logger = LoggerFactory.getLogger(ClockService.class);

    public ClockService() {
        clock = Clock.system(ZoneId.of("Europe/Warsaw"));
    }

    public Date getCurrentDate() {
        logger.info("Returning current date.");
        return Date.from(clock.instant());
    }
}
