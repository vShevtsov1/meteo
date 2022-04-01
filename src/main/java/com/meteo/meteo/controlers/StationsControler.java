package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.entities.Stations;
import com.meteo.meteo.services.StationServices;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all stations data")
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
    public List<Stations> getByOwnerMail(@PathVariable("email") String email) {
        return stationServices.getStationsByOwnerEmail(email);
    }

    @Operation(summary = "Save a new station")
    @PostMapping("/save")
    public ResponseEntity<Stations> saveNewStations(@RequestBody Stations newStation) {
        try {
            return ResponseEntity.ok(stationServices.save(newStation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update info about the station")
    @PostMapping("/update")
    public ResponseEntity<Stations> updateStations(@RequestBody Stations stations) {
        try {
            return ResponseEntity.ok(stationServices.save(stations));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get tok for the station")
    @GetMapping(path = "/generate/token")
    public ResponseEntity<JwtDTO> generateToken(Stations stations) {
        try {
            return ResponseEntity.ok(stationServices.generateToken(stations));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
