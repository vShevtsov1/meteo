package com.meteo.meteo.services;

import com.meteo.meteo.Constants;
import com.meteo.meteo.entities.Stations;
import com.meteo.meteo.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenServices {
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateTokenUser(User user) {
        Date date = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        String jws = Jwts.builder().
                setSubject(user.getMail()).
                claim("role", user.getRole()).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        return jws;
    }
    public String generateTokenStation(Stations station) {
        Date date = Date.from(Instant.now().plus(1, ChronoUnit.YEARS));
        String jws = Jwts.builder().
                setSubject(String.valueOf(station.getIdStations())).
                claim("role", Constants.station.toString()).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        System.out.println(jwtSecret);
        return jws;
    }
    public String activationToken(String email) {
        Date date = Date.from(Instant.now().plus(24, ChronoUnit.HOURS));
        String jws = Jwts.builder().
                setSubject(email).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        System.out.println(jwtSecret);
        return jws;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }

    public String getMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
