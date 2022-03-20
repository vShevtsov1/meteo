package com.meteo.meteo;


import com.meteo.meteo.services.UserServices;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@SpringBootApplication
public class MeteoApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	public static void main(String[] args) throws Exception {


        SpringApplication.run(MeteoApplication.class, args);

    }

}
