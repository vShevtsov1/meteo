package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.entities.Stations;


import com.meteo.meteo.services.StationServices;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stations")
public class StationsControler {

    private StationServices stationServices;

    public StationsControler(StationServices stationServices) {
        this.stationServices = stationServices;
    }

    @Operation(summary = "Get all stations")
    @GetMapping()
    public @ResponseBody
    Iterable<Stations> getAll() {
        return stationServices.findAll();
    }

    @Operation(summary = "Get station by id")
    @GetMapping(path = "id/{id}")
    public Stations getById(@PathVariable("id") long id) {
        return stationServices.getStationsByIdStations(id);
    }

    @Operation(summary = "Get station by owner e-mail")
    @GetMapping(path = "ownermail/{email}")
    public List<Stations> getByOwnerMail(@PathVariable("email") String email)
    {
        return stationServices.getStationsByOwnerEmail(email);
    }

    @Operation(summary = "Save a new station")
    @PostMapping("/save")
    public void saveNewStations(@RequestBody Stations newStation) {
        stationServices.save(newStation);
    }

    @Operation(summary = "update info about the station")
    @PostMapping("/update")
    public ResponseEntity updateStations(@RequestBody Stations stations)
    {
        stationServices.save(stations);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
