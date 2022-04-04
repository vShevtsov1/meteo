package com.meteo.meteo.services;


import com.meteo.meteo.DTO.CompiledDataDTO;
import com.meteo.meteo.entities.CompiledData;
import com.meteo.meteo.repositories.CompiledDataRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class CompiledDataService {
    private final CompiledDataRepository compiledDataRepository;


    public CompiledDataService(CompiledDataRepository compiledDataRepository) {
        this.compiledDataRepository = compiledDataRepository;
    }

    public Date getDateBefore(long days) {
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(days));
        return Date.from(before);
    }

    public CompiledData setDataToTable(CompiledDataDTO json) {
        return compiledDataRepository.save(new CompiledData(json.getStationId(), json.getDatetime(), json.getSensor(), json.getUnit(), json.getValue()));
    }

    public Iterable<CompiledData> findAll() {
        return compiledDataRepository.findAll();
    }

    public List<CompiledData> getAllByStationId(long stationId) {
        return compiledDataRepository.getAllByStationId(stationId);
    }

    public List<CompiledData> getAllByStationIdAndSensor(long stationId, String sensor) {
        return compiledDataRepository.getAllByStationIdAndSensor(stationId, sensor);
    }

    public List<CompiledData> getAllByStationIdAndSensorAndDatetimeAfter(String sensor,
                                                                         String station_id,
                                                                         Date date) {
        return compiledDataRepository.getAllByStationIdAndSensorAndDatetimeAfter(sensor, station_id, date);
    }

    public String getaverage(String sensor,
                             String station_id,
                             Date date) {
        return compiledDataRepository.getaverage(sensor, station_id, date);
    }
}
