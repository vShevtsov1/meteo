package com.meteo.meteo.controlers;

import com.meteo.meteo.entities.CompiledData;


import com.meteo.meteo.interfaces.CompiledDataRepository;
import com.meteo.meteo.services.CompiledDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/compileddata")
public class CompiledDataControler {


    private CompiledDataRepository compiledDataRepoditory;
    private CompiledDataService compiledDataService;

    public CompiledDataControler(CompiledDataRepository compiledDataRepoditory, CompiledDataService compiledDataService) {
        this.compiledDataRepoditory = compiledDataRepoditory;
        this.compiledDataService = compiledDataService;
    }

    @Operation(summary = "Get all data")
    @GetMapping()
    public Iterable<CompiledData> getAll()
     {
         return compiledDataRepoditory.findAll();
     }
    @Operation(summary = "Get all data by station id")
     @GetMapping(path = "id/{id}")
    public List<CompiledData> getById(@Parameter(description = "station id")
            @PathVariable("id") long id)
     {
         return compiledDataRepoditory.getAllByStationId(id);
     }

    @Operation(summary = "Get all data by station id and sensor")
     @GetMapping(path = "idandsensor/{id}/{sensor}")
     public List<CompiledData> getAllByIdAndSensor(@Parameter(description = "station id")
                                                       @PathVariable("id") long id,
                                                   @Parameter(description = "sensor of station")
                                                       @PathVariable("sensor")String sensor)
     {
         return compiledDataRepoditory.getAllByStationIdAndSensor(id,sensor);
     }

    @Operation(summary = "Get all data by station id, sensor and the date the data was sent ")
    @GetMapping(path = "last-values/{id}/{sensor}/{days}")
    public List<CompiledData> getAllByIdAndSensorAndDate(@Parameter(description = "station id")
                                                             @PathVariable("id") String id,
                                                         @Parameter(description = "sensor of station")
                                                         @PathVariable("sensor")String sensor,
                                                         @Parameter(description = "date when the data was sent")
                                                         @PathVariable("days") long days)
    {
        return compiledDataRepoditory.getAllByStationIdAndSensorAndDatetimeAfter(sensor,id, compiledDataService.getDateBefore(days));
    }
    @Operation(summary = "Return average value")
    @GetMapping(path = "last-avg/{id}/{sensor}/{days}")
    public String avgValue(@Parameter(description = "station id")
                               @PathVariable("id") String id,
                           @Parameter(description = "sensor of station")
                               @PathVariable("sensor")String sensor,
                           @Parameter(description = "date when the data was sent")
                               @PathVariable("days") long days)
    {
        return compiledDataRepoditory.getaverage(sensor,id,compiledDataService.getDateBefore(days));
    }
    @Operation(summary = "Save values received from stations to database with a check for the validity of the user token")

    @GetMapping ("/savevalue")
    public String save(@Parameter(description = "Received json with data from station and token") @RequestBody String json) throws ParseException {
        compiledDataService.setDataToTable(json);
        return json;
    }
}
