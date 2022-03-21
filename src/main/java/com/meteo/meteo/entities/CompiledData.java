package com.meteo.meteo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CompiledData")
public class CompiledData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private long stationId;
    private Date datetime;
    private String sensor;
    private String unit;
    private String value;

    public CompiledData(long id, long stationId, Date datetime, String sensor, String unit, String value) {
        this.id = id;
        this.stationId = stationId;
        this.datetime = datetime;
        this.sensor = sensor;
        this.unit = unit;
        this.value = value;
    }

    public CompiledData() {

    }

    public CompiledData(long stationId, Date datetime, String sensor, String unit, String value) {
        this.stationId = stationId;
        this.datetime = datetime;
        this.sensor = sensor;
        this.unit = unit;
        this.value = value;
    }
}
