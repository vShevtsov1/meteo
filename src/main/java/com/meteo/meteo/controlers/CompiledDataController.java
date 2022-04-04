package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.CompiledDataDTO;
import com.meteo.meteo.entities.CompiledData;
import com.meteo.meteo.services.CompiledDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/compileddata")
public class CompiledDataController {


    private final CompiledDataService compiledDataService;

    public CompiledDataController(CompiledDataService compiledDataService) {
        this.compiledDataService = compiledDataService;
    }
    @Operation(summary = "Get all data")
    @GetMapping()
    public Iterable<CompiledData> getAll() {
        return compiledDataService.findAll();
    }

    @Operation(summary = "Get all data by station id")
    @GetMapping(path = "id/{id}")
    public List<CompiledData> getById(@Parameter(description = "station id")
                                      @PathVariable("id") long id) {
        return compiledDataService.getAllByStationId(id);
    }

    @Operation(summary = "Get all data by station id and sensor")
    @GetMapping(path = "idandsensor/{id}/{sensor}")
    public List<CompiledData> getAllByIdAndSensor(@Parameter(description = "station id")
                                                  @PathVariable("id") long id,
                                                  @Parameter(description = "sensor of station")
                                                  @PathVariable("sensor") String sensor) {
        return compiledDataService.getAllByStationIdAndSensor(id, sensor);
    }

    @Operation(summary = "Get all data by station id, sensor and the date the data was sent ")
    @GetMapping(path = "last-values/{id}/{sensor}/{days}")
    public List<CompiledData> getAllByIdAndSensorAndDate(@Parameter(description = "station id")
                                                         @PathVariable("id") String id,
                                                         @Parameter(description = "sensor of station")
                                                         @PathVariable("sensor") String sensor,
                                                         @Parameter(description = "date when the data was sent")
                                                         @PathVariable("days") long days) {
        return compiledDataService.getAllByStationIdAndSensorAndDatetimeAfter(sensor, id, compiledDataService.getDateBefore(days));
    }

    @Operation(summary = "Return average value")
    @GetMapping(path = "last-avg/{id}/{sensor}/{days}")
    public String avgValue(@Parameter(description = "station id")
                           @PathVariable("id") String id,
                           @Parameter(description = "sensor of station")
                           @PathVariable("sensor") String sensor,
                           @Parameter(description = "date when the data was sent")
                           @PathVariable("days") long days) {
        return compiledDataService.getaverage(sensor, id, compiledDataService.getDateBefore(days));
    }

    @Operation(summary = "Save value received from stations to database")
    @GetMapping("/savevalue")
    public ResponseEntity<CompiledData> save(@Parameter(description = "Received json with data from station and token") CompiledDataDTO compiledDataDTO) {
        try {
            return ResponseEntity.ok(compiledDataService.setDataToTable(compiledDataDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
