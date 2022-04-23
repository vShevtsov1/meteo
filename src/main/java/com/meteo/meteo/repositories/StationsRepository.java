package com.meteo.meteo.repositories;

import com.meteo.meteo.entities.Stations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationsRepository extends CrudRepository<Stations,Integer> {
    Stations getStationsByIdStations(long id);
    List<Stations> getStationsByOwnerEmail(String email);
    //@Query("select s.id_stations,s.altitude,s.description,s.latitude,s.location,s.longitude,s.name,s.owner_email,s.sensors,s.type from stations s join compiled_data c on s.id_stations = c.station_id where c.datetime>DATE_ADD(NOW(), INTERVAL -1 MONTH)")
    //List<Stations> getAllActiveStation();
}
