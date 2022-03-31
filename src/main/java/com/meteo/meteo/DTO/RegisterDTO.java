package com.meteo.meteo.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String surname;
    private String dateOfBirth;
    private String email;
    private String password;

    public RegisterDTO(String name, String surname, String dateOfBirth, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
    }
}
