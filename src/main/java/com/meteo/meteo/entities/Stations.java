package com.meteo.meteo.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="stations")
public class Stations {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idStations;
    private String type;
    private Double altitude;
    private String description;
    private Double latitude;
    private String location;
    private Double longitude;
    private String name;
    private String ownerEmail;
    private String sensors;

}
