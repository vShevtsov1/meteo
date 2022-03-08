package com.meteo.meteo.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idUser;
    private String name;
    private String surname;
    private String  dateOfBirth;
    private String mail;
    private String role;
    private String password;
}
