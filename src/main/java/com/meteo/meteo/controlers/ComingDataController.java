package com.meteo.meteo.controlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteo.meteo.entities.ComingData;
import com.meteo.meteo.entities.CompiledData;
import com.meteo.meteo.entities.Stations;
import com.meteo.meteo.interfaces.ComingDataInterface;
import com.meteo.meteo.interfaces.CompiledDataInterface;
import com.meteo.meteo.interfaces.stationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/ComingData")
public class ComingDataController {

    @Autowired
    private ComingDataInterface comingDataInterface;
    @Autowired
    private stationsInterface stationsInterface;
    @Autowired
    private CompiledDataInterface compiledDataInterface;

    @GetMapping(path = "all")
    public Iterable<ComingData> getAll()
    {
        return comingDataInterface.findAll();
    }

     @GetMapping("getDataByToken/{id}")
    public List<ComingData> getDataByToken(@PathVariable("id") long id){
        return comingDataInterface.getAllByTokenId(id);
    }

    @GetMapping(path= "getComingData")
    public List<String> getAllComingData(){
        return comingDataInterface.getAllComingData();
    }
    @GetMapping("/saveJSONtoTable")
    public void saveJSONintoTable(@RequestBody String json)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> result = null;
        try {
            result = objectMapper.readValue(json, HashMap.class);
            System.out.println(result);
        }
                catch(Exception e) {
                    e.printStackTrace();
                }

        Stations st = stationsInterface.getStationsByIdStations(Long.valueOf(result.get("idStations").toString()));
        System.out.println(st);
        LocalDateTime localDateTime = LocalDateTime.now();
        ComingData sup = new ComingData(st.getTokenId(), localDateTime,json);

        comingDataInterface.save(sup);
        Date date = new Date();
        CompiledData compiledData = new CompiledData(Long.valueOf(result.get("idStations").toString()),
                date, result.get("sensor").toString(), result.get("unit").toString(), result.get("value").toString());
        compiledDataInterface.save(compiledData);
            }



/*
    @GetMapping("getLastDataByToken")
    public ComingData getLastDataByToken(@PathVariable("id") long id){
        return comingDataInterface.getLastDataByToken(id);
    }*/
}

