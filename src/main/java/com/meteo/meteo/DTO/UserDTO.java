package com.meteo.meteo.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private long idUser;
    private String name;
    private String surname;
    private String  dateOfBirth;
    private String mail;
    private String role;
}
