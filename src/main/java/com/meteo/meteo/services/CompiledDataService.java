package com.meteo.meteo.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.meteo.meteo.entities.CompiledData;
import com.meteo.meteo.interfaces.CompiledDataRepository;
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

    public Object setDataToTable(String json) throws ParseException {
        ObjectMapper json1 = new ObjectMapper();
        Map<String, Object> mapa = null;
        try {
            mapa = json1.readValue(json, HashMap.class);
        } catch (JsonMappingException ex) {
            ex.printStackTrace();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        CompiledData compData = new CompiledData( Long.parseLong(String.valueOf(mapa.get("stationId"))), new SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(mapa.get("datetime"))), (String) mapa.get("sensor"),
                (String) mapa.get("unit"), (String) mapa.get("value"));
        System.out.println(mapa);
        String token = String.valueOf(mapa.get("token"));

        if (userServices.validateToken(token) == true) {
            compiledDataRepository.save(compData);
            return token;
        } else {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }
}
