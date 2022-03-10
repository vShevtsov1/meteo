package com.meteo.meteo.interfaces;
import com.meteo.meteo.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u from User u")
    List<User> getAll();
    User getUserByIdUser(long id);
    User getUserByMailAndPassword(String mail, String password);
    User getUserByMail(String login);
}
