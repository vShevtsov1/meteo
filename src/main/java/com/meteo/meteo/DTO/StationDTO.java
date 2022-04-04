package com.meteo.meteo.DTO;


import lombok.Data;

@Data
public class StationDTO {
    private String type;
    private Double altitude;
    private String description;
    private Double latitude;
    private String location;
    private Double longitude;
    private String name;
    private String sensors;

    public StationDTO(String type, Double altitude, String description, Double latitude, String location, Double longitude, String name, String sensors) {
        this.type = type;
        this.altitude = altitude;
        this.description = description;
        this.latitude = latitude;
        this.location = location;
        this.longitude = longitude;
        this.name = name;
        this.sensors = sensors;
    }

    public StationDTO() {
    }
}
