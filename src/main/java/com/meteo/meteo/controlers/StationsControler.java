package com.meteo.meteo.controlers;

import com.meteo.meteo.entities.Stations;


import com.meteo.meteo.interfaces.StationsRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stations")
public class StationsControler {

    private StationsRepository stationsRepository;

    public StationsControler(StationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }


    @Operation(summary = "Get all stations")
    @GetMapping()
    public @ResponseBody
    Iterable<Stations> getAll() {
        return stationsRepository.findAll();
    }

    @Operation(summary = "Get station by id")
    @GetMapping(path = "id/{id}")
    public Stations getById(@PathVariable("id") long id) {
        return stationsRepository.getStationsByIdStations(id);
    }

    @Operation(summary = "Get station by owner e-mail")
    @GetMapping(path = "ownermail/{email}")
    public List<Stations> getByOwnerMail(@PathVariable("email") String email)
    {
        return stationsRepository.getStationsByOwnerEmail(email);
    }

    @Operation(summary = "Save a new station")
    @PostMapping("/save")
    public void saveNewStations(@RequestBody Stations newStation) {
        stationsRepository.save(newStation);
    }

    @Operation(summary = "update info about the station")
    @PostMapping("/update")
    public ResponseEntity updateStations(@RequestBody Stations stations)
    {
        stationsRepository.save(stations);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
