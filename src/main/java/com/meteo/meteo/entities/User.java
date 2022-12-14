package com.meteo.meteo.entities;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name="users",uniqueConstraints = { @UniqueConstraint(columnNames = { "mail"})})
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idUser;
    private String name;
    private String surname;
    private String  dateOfBirth;
    private String mail;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private String password;
    private Boolean active;

    public User(String name, String surname, String dateOfBirth, String mail, Roles role, String password,Boolean active) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.mail = mail;
        this.role = role;
        this.password = password;
        this.active = active;
    }

    public User() {

    }
}
