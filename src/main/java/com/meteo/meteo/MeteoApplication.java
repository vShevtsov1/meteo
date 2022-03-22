package com.meteo.meteo;


import com.meteo.meteo.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MeteoApplication {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    public static void main(String[] args){
        SpringApplication.run(MeteoApplication.class, args);
    }

}
