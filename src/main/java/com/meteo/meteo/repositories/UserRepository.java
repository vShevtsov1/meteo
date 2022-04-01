package com.meteo.meteo.repositories;
import com.meteo.meteo.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u from User u")
    List<User> getAll();
    User getUserByIdUser(long id);
    User getUserByMailAndPassword(String mail, String password);
    User getUserByMail(String login);

    @Query("select u from User  u where mail = :login")
    User getUserForLogin(String login);
    @Transactional
    @Modifying
    @Query("update User u set u.active = true where u.idUser =:id")
    void updateUserActivation(long id);
}
