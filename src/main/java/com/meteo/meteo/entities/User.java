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

    public User(String name, String surname, String dateOfBirth, String mail, String role, String password) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.mail = mail;
        this.role = role;
        this.password = password;
    }

    public User() {

    }


}
