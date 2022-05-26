package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.DTO.StationDTO;
import com.meteo.meteo.DTO.UserDTO;
import com.meteo.meteo.entities.Stations;
import com.meteo.meteo.services.StationServices;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/stations")
public class StationsController {

    private final StationServices stationServices;
    private final ModelMapper modelMapper;

    public StationsController(StationServices stationServices, ModelMapper modelMapper) {
        this.stationServices = stationServices;
        this.modelMapper = modelMapper;
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
    public List<Stations> getByOwnerMail(Authentication authentication) {
        return stationServices.getStationsByOwnerEmail((String) authentication.getPrincipal());
    }

    @Operation(summary = "Save a new station")
    @PostMapping("/save")
    public ResponseEntity<Stations> saveNewStations(@RequestBody StationDTO newStation, Authentication authentication) {
        try {
            Stations stations = stationServices.save(modelMapper.map(newStation, Stations.class));
            stations.setOwnerEmail((String) authentication.getPrincipal());
            return ResponseEntity.ok(stationServices.save(stations));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update info about the station")
    @PostMapping("/update/{id}")
    public ResponseEntity<Stations> updateStations(@RequestBody StationDTO stationDTO,
                                                   Authentication authentication,
                                                   @RequestParam("id") long id) {
        try {
            Stations stations = stationServices.save(modelMapper.map(stationDTO, Stations.class));
            stations.setOwnerEmail((String) authentication.getPrincipal());
            stations.setIdStations(id);
            return ResponseEntity.ok(stationServices.save(stations));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get tok for the station")
    @GetMapping(path = "/generate/token")
    public ResponseEntity<JwtDTO> generatetoken(@RequestParam long id,Authentication authentication) {
        try {
            return ResponseEntity.ok(stationServices.generateToken(id,authentication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(summary = "Get all active station")
    @GetMapping(path = "/active")
    public List<Stations> getAllActive()
    {
        return stationServices.getAllActive();
    }
}
