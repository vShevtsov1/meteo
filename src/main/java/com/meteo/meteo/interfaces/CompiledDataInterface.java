package com.meteo.meteo.interfaces;

import com.meteo.meteo.entities.CompiledData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CompiledDataInterface extends CrudRepository<CompiledData,Integer> {

    List<CompiledData> getAllByStationId(long stationId);
    List<CompiledData> getAllByStationIdAndSensor(long stationId, String sensor);
    @Query("select c from CompiledData c where sensor = :sensor and station_id = :station_id and datetime>:date")
    List<CompiledData> getAllByStationIdAndSensorAndDatetimeAfter(@Param("sensor") String sensor,
                                                                  @Param("station_id") String station_id,
                                                                  @Param("date") Date date);

    @Query("select avg(value) from CompiledData c where sensor = :sensor and station_id = :station_id and datetime>:date")
    String getaverage(@Param("sensor") String sensor,
                      @Param("station_id") String station_id,
                      @Param("date") Date date);
   

}
