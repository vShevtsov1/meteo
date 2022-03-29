package com.meteo.meteo.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String surname;
    private String  dateOfBirth;
    private String mail;
    private String password;

    public RegisterDTO(String name, String surname, String dateOfBirth, String mail, String password) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.mail = mail;
        this.password = password;
    }
}
