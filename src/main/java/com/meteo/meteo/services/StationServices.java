package com.meteo.meteo.services;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.Exceptions.OwnerException;
import com.meteo.meteo.entities.Stations;
import com.meteo.meteo.repositories.StationsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServices {


    private final StationsRepository stationsRepository;
    private final TokenServices tokenServices;

    public StationServices(StationsRepository stationsRepository, TokenServices tokenServices) {
        this.stationsRepository = stationsRepository;
        this.tokenServices = tokenServices;
    }

    public Iterable<Stations> findAll() {
        return stationsRepository.findAll();
    }

    public Stations getStationsByIdStations(long id) {
        return stationsRepository.getStationsByIdStations(id);
    }

    public List<Stations> getStationsByOwnerEmail(String email) {
        return stationsRepository.getStationsByOwnerEmail(email);
    }

    public Stations save(Stations newStation) {
        return stationsRepository.save(newStation);
    }

    public JwtDTO generateToken(long stations, Authentication authentication) throws Exception {
        if(stationsRepository.getStationsByIdStations(stations).getOwnerEmail().equals(authentication.getPrincipal()))
        {
            return new JwtDTO(tokenServices.generateTokenStation(stations));

        }
        throw new OwnerException("Bad owner");
    }

   /* public List<Stations> getAllActive(){return  stationsRepository.getAllActiveStation();}*/

}
