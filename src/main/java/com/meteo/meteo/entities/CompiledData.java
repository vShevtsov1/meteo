package com.meteo.meteo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "CompiledData")
public class CompiledData {

    @Id
    private long stationId;
    private Date datetime;
    private String sensor;
    private String unit;
    private String value;

}
