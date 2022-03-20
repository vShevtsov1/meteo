package com.meteo.meteo.services;

import com.meteo.meteo.entities.User;
import com.meteo.meteo.interfaces.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class UserServices {
    @Value("$(jwt.secret)")
    private String jwtSecret;
    private UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(User user) {
        Date date = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        String jws = Jwts.builder().
                setSubject(user.getMail()).
                claim("name",user.getName()).
                claim("surname",user.getSurname()).
                claim("role",user.getRole()).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256,jwtSecret).compact();
        return jws;
    }

    public Object login(String login,String password)
    {
        User userlogin = null;
        userlogin = userRepository.getUserByMailAndPassword(login,password);
        if(userlogin==null)
        {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        else {
            String f = generateToken(userlogin);
            return f;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt");
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature");
        } catch (Exception e) {
            System.out.println("invalid token");
        }
        return false;
    }


}
