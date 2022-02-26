package com.meteo.meteo.interfaces;

import com.meteo.meteo.entities.CompiledData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompiledDataInterface extends CrudRepository<CompiledData,Integer> {

    List<CompiledData> getAllByStationId(long stationId);
    List<CompiledData> getAllByStationIdAndSensor(long stationId, String sensor);
    List<CompiledData> getAllByStationIdAndSensorAndDatetimeAfter(long id,String sensor, String date);

}
