package com.meteo.meteo.repositories;

import com.meteo.meteo.entities.Stations;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationsRepository extends CrudRepository<Stations,Integer> {
    Stations getStationsByIdStations(long id);
    List<Stations> getStationsByOwnerEmail(String email);
}
