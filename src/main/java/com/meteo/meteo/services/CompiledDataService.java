package com.meteo.meteo.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.meteo.meteo.DTO.CompiledDataDTO;
import com.meteo.meteo.entities.CompiledData;
import com.meteo.meteo.exceptions.TokenException;
import com.meteo.meteo.interfaces.CompiledDataRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompiledDataService {
    private CompiledDataRepository compiledDataRepository;
    private UserServices userServices;


    public CompiledDataService(CompiledDataRepository compiledDataRepository, UserServices userServices) {
        this.compiledDataRepository = compiledDataRepository;
        this.userServices = userServices;
    }

    public Date getDateBefore(long days)
    {
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(days));
        Date dateBefore = Date.from(before);
        return dateBefore;
    }

    public void setDataToTable(CompiledDataDTO json) throws ParseException {
       compiledDataRepository.save(new CompiledData(json.getStationId(), json.getDatetime(), json.getSensor(), json.getUnit(), json.getValue()));
    }

    public Iterable<CompiledData> findAll()
    {
        return compiledDataRepository.findAll();
    }

    public List<CompiledData> getAllByStationId(long stationId){
        return compiledDataRepository.getAllByStationId(stationId);
    }

    public List<CompiledData> getAllByStationIdAndSensor(long stationId, String sensor){
        return compiledDataRepository.getAllByStationIdAndSensor(stationId, sensor);
    }

    public List<CompiledData> getAllByStationIdAndSensorAndDatetimeAfter(String sensor,
                                                                  String station_id,
                                                                  Date date){
        return compiledDataRepository.getAllByStationIdAndSensorAndDatetimeAfter(sensor, station_id, date);
    }

    public String getaverage(String sensor,
                             String station_id,
                             Date date){
        return compiledDataRepository.getaverage(sensor, station_id, date);
    }
}
