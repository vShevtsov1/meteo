package com.meteo.meteo.controlers;

import com.meteo.meteo.entities.CompiledData;
import com.meteo.meteo.interfaces.CompiledDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/CompiledData")
public class CompiledDataControler {

    @Autowired
    private CompiledDataInterface compiledDataInterface;

     @GetMapping(path = "all")
    public Iterable<CompiledData> getAll()
     {
         return compiledDataInterface.findAll();
     }

     @GetMapping(path = "getAllById/{id}")
    public List<CompiledData> getById(@PathVariable("id") long id)
     {
         return compiledDataInterface.getAllByStationId(id);
     }

     @GetMapping(path = "getAllByIdAndSensor/{id}/{sensor}")
     public List<CompiledData> getAllByIdAndSensor(@PathVariable("id") long id, @PathVariable("sensor")String sensor)
     {
         return compiledDataInterface.getAllByStationIdAndSensor(id,sensor);
     }
    @GetMapping(path = "getAllByIdAndSensorAndDate/{id}/{sensor}/{date}")
    public List<CompiledData> getAllByIdAndSensorAndDate(@PathVariable("id") String id,
                                                         @PathVariable("sensor")String sensor,
                                                         @PathVariable("date") String date)
    {
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(Long.parseLong(date)));
        Date dateBefore = Date.from(before);
        return compiledDataInterface.getAllByStationIdAndSensorAndDatetimeAfter(sensor,id,dateBefore);
    }
    @GetMapping(path = "avg/{id}/{sensor}/{date}")
    public String avgValue(@PathVariable("id") String id,
                      @PathVariable("sensor")String sensor,
                      @PathVariable("date") String date)
    {
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(Long.parseLong(date)));
        Date dateBefore = Date.from(before);
        String c = compiledDataInterface.getaverage(sensor,id,dateBefore);
        if(c!=null){
            return c;
        }
        else
        {
            return "null";
        }
    }
}
