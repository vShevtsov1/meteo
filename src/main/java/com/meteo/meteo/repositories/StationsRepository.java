package com.meteo.meteo.repositories;

import com.meteo.meteo.entities.Stations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StationsRepository extends CrudRepository<Stations,Integer> {
    Stations getStationsByIdStations(long id);
    List<Stations> getStationsByOwnerEmail(String email);
    @Query("select DISTINCT s.id_stations,s.altitude,s.description,s.latitude,s.location,s.longitude,s.name,s.owner_email,s.sensors,s.type from stations s join compiled_data c on s.id_stations = c.station_id where c.datetime>:date")
    List<Stations> getAllActiveStation(@Param("date") Date date);
}
