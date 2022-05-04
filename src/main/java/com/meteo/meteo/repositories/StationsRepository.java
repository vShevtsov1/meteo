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
    @Query("select DISTINCT s from Stations s, CompiledData c where c.stationId=s.idStations and c.datetime>:date")
    List<Stations> getAllActiveStation(@Param("date") Date date);
}
