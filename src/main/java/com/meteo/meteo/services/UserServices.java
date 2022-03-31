package com.meteo.meteo.services;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.DTO.LoginDTO;
import com.meteo.meteo.DTO.RegisterDTO;
import com.meteo.meteo.DTO.UserDTO;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.exceptions.TokenException;
import com.meteo.meteo.interfaces.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class UserServices {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Activation activation;

    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder, Activation activation) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activation = activation;
    }

    public String generateToken(User user) {
        Date date = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        String jws = Jwts.builder().
                setSubject(user.getMail()).
                claim("name", user.getName()).
                claim("surname", user.getSurname()).
                claim("role", user.getRole()).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        System.out.println(jwtSecret);
        return jws;
    }
    public JwtDTO login(LoginDTO loginDTO) {
        User userlogin =userRepository.getUserForLogin(loginDTO.getUsername());
        if (!passwordEncoder.matches(loginDTO.getPassword(), userlogin.getPassword()) || userlogin == null||userlogin.getActive()==true
        ) {
            return null;
        }
        {
            return new JwtDTO( generateToken(userlogin));
        }
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            new TokenException("invalid token");
        }
        return false;
    }
    public String getUserRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }
    public String getUserMail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getUserByIdUser(long id) {
        return userRepository.getUserByIdUser(id);
    }

    public User getUserByMail(String login) {
        return userRepository.getUserByMail(login);
    }

    public User save(RegisterDTO registerDTO) {
         User user = userRepository.save(new User(registerDTO.getName(),registerDTO.getSurname(),registerDTO.getDateOfBirth()
         ,registerDTO.getMail(),"user",passwordEncoder.encode(registerDTO.getPassword()),false));
        activation.sendEmail(activation.activationToken(user.getMail()));
        return user;
    }

    public void update(long id)
    {
        userRepository.updateUserActivation(id);
    }
    public Object changeActivation(String token)
    {
        if(validateToken(token)){
            User user = getUserByMail(getUserMail(token));
            update(user.getIdUser());
            return ResponseEntity.ok();
        }
        else {
            return ResponseEntity.badRequest();
        }
    }
}

