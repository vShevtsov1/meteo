package com.meteo.meteo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class CompiledDataDTO {
    private long stationId;
    private Date datetime;
    private String sensor;
    private String unit;
    private String value;
}
