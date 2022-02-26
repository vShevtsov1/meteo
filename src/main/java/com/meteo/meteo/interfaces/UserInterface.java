package com.meteo.meteo.interfaces;

import com.meteo.meteo.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInterface extends CrudRepository<User, Integer> {
    User getUserByIdUser(long id);
    User getUserByMailAndPassword(String mail, String password);
    User getUserByMail(String login);
}
