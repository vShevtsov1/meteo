package com.meteo.meteo.services;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class CompiledDataService {
    public Date getDateBefore(long days)
    {
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(days));
        Date dateBefore = Date.from(before);
        return dateBefore;
    }
}
