package com.meteo.meteo.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="comingData")
public class ComingData {
    @Id
    private String tokenId;
    private LocalDateTime received;
    private String comingData;

    public ComingData(String tokenId, LocalDateTime received, String comingData) {
        this.tokenId = tokenId;
        this.received = received;
        this.comingData = comingData;
    }
}
