package com.meteo.meteo.controlers;

import com.meteo.meteo.entities.Stations;


import com.meteo.meteo.interfaces.stationsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping(path = "/stations")
public class StationsControler {

    private stationsRepository stationsRepository;

    public StationsControler(stationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }


    protected static SecureRandom random = new SecureRandom();

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Stations> getAll() {
        return stationsRepository.findAll();
    }

    @GetMapping(path = "getById/{id}")
    public Stations getById(@PathVariable("id") long id) {
        return stationsRepository.getStationsByIdStations(id);
    }

    @GetMapping(path = "getByOwnerMail/{email}")
    public List<Stations> getByOwnerMail(@PathVariable("email") String email)
    {
        return stationsRepository.getStationsByOwnerEmail(email);
    }

    @PostMapping("/save")
    public void saveNewStations(@RequestBody Stations newStation) {
        stationsRepository.save(newStation);
    }
    @PostMapping("/update")
    public ResponseEntity updateStations(@RequestBody Stations stations)
    {
        stationsRepository.save(stations);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
