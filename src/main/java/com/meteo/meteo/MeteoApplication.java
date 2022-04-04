package com.meteo.meteo;


import com.meteo.meteo.entities.User;
import com.meteo.meteo.services.TokenServices;
import com.meteo.meteo.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import java.net.MalformedURLException;
import java.net.URL;


@SpringBootApplication
public class MeteoApplication {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}


    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(MeteoApplication.class, args);
    }
}
