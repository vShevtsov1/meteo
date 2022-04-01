package com.meteo.meteo.entities;

import com.meteo.meteo.Roles;
import lombok.Data;
import org.hibernate.annotations.Type;

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
    private Boolean active;

    public User(String name, String surname, String dateOfBirth, String mail, String role, String password,Boolean active) {
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
