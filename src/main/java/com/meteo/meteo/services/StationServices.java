package com.meteo.meteo.services;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.entities.Stations;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.exceptions.TokenException;
import com.meteo.meteo.interfaces.StationsRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class StationServices {
    @Value("${jwt.secret}")
    private String jwtSecret;


    StationsRepository stationsRepository;

    public StationServices(StationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }

    public String generateToken(Stations station) {
        Date date = Date.from(Instant.now().plus(1, ChronoUnit.YEARS));
        String jws = Jwts.builder().
                setSubject(String.valueOf(station.getIdStations())).
                claim("owner",station.getOwnerEmail()).
                claim("name",station.getName()).
                claim("type",station.getType()).
                claim("description",station.getDescription()).
                claim("altitude",station.getAltitude()).
                claim("latitude",station.getLatitude()).
                claim("location",station.getLocation()).
                claim("longitude",station.getLongitude()).
                claim("sensors",station.getSensors()).
                claim("role", "station").
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256,jwtSecret).compact();
        System.out.println(jwtSecret);
        return jws;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            new TokenException("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            new TokenException("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            new TokenException("Malformed jwt");
        } catch (SignatureException sEx) {
            new TokenException("Invalid signature");
        } catch (Exception e) {
            new TokenException("invalid token");
        }
        return false;
    }


    public Iterable<Stations> findAll() {
        return stationsRepository.findAll();
    }

    public Stations getStationsByIdStations(long id) {
        return stationsRepository.getStationsByIdStations(id);
    }

    public List<Stations> getStationsByOwnerEmail(String email)
    {
        return stationsRepository.getStationsByOwnerEmail(email);
    }

    public void save(Stations newStation) {
        stationsRepository.save(newStation);
    }



}
