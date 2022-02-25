package com.meteo.meteo.interfaces;

import com.meteo.meteo.entities.Stations;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface stationsInterface extends CrudRepository<Stations,Integer> {
    Stations getStationsByIdStations(long id);
    List<Stations> getStationsByOwnerEmail(String email);
    List<Stations> getStationsByTokenId(String tokenId);
}
