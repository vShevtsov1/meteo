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

    private stationsRepository stationsInterface;

    public StationsControler(stationsRepository stationsInterface) {
        this.stationsInterface = stationsInterface;
    }

    public StationsControler(stationsInterface stationsInterface) {
        this.stationsInterface = stationsInterface;
    }

    protected static SecureRandom random = new SecureRandom();

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Stations> getAll() {
        return stationsInterface.findAll();
    }

    @GetMapping(path = "getById/{id}")
    public Stations getById(@PathVariable("id") long id) {
        return stationsInterface.getStationsByIdStations(id);
    }

    @GetMapping(path = "getByOwnerMail/{email}")
    public List<Stations> getByOwnerMail(@PathVariable("email") String email)
    {
        return stationsInterface.getStationsByOwnerEmail(email);
    }

    @GetMapping("/save")
    public String saveNewStations(@RequestBody Stations newStation) {
        long longToken = Math.abs( random.nextLong() );
        String random = Long.toString( longToken, 16 );
        newStation.setTokenId(random);
        stationsInterface.save(newStation);
        return newStation.getTokenId();
    }
    @GetMapping("/update")
    public ResponseEntity updateStations(@RequestBody Stations stations)
    {
        stationsInterface.save(stations);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
