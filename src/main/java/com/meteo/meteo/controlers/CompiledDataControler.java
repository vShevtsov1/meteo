package com.meteo.meteo.controlers;

import com.meteo.meteo.entities.CompiledData;


import com.meteo.meteo.interfaces.CompiledDataRepository;
import com.meteo.meteo.services.CompiledDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/CompiledData")
public class CompiledDataControler {


    private CompiledDataRepository compiledDataRepoditory;
    private CompiledDataService compiledDataService;

    public CompiledDataControler(CompiledDataRepository compiledDataRepoditory, CompiledDataService compiledDataService) {
        this.compiledDataRepoditory = compiledDataRepoditory;
        this.compiledDataService = compiledDataService;
    }

    @GetMapping()
    public Iterable<CompiledData> getAll()
     {
         return compiledDataRepoditory.findAll();
     }

     @GetMapping(path = "id/{id}")
    public List<CompiledData> getById(@PathVariable("id") long id)
     {
         return compiledDataRepoditory.getAllByStationId(id);
     }

     @GetMapping(path = "idandsensor/{id}/{sensor}")
     public List<CompiledData> getAllByIdAndSensor(@PathVariable("id") long id, @PathVariable("sensor")String sensor)
     {
         return compiledDataRepoditory.getAllByStationIdAndSensor(id,sensor);
     }

    @GetMapping(path = "last-values/{id}/{sensor}/{days}")
    public List<CompiledData> getAllByIdAndSensorAndDate(@PathVariable("id") String id,
                                                         @PathVariable("sensor")String sensor,
                                                         @PathVariable("days") long days)
    {
        return compiledDataRepoditory.getAllByStationIdAndSensorAndDatetimeAfter(sensor,id, compiledDataService.getDateBefore(days));
    }
    @GetMapping(path = "last-avg/{id}/{sensor}/{days}")
    public String avgValue(@PathVariable("id") String id,
                      @PathVariable("sensor")String sensor,
                      @PathVariable("days") long days)
    {
        return compiledDataRepoditory.getaverage(sensor,id,compiledDataService.getDateBefore(days));
    }
}
