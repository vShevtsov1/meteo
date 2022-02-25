package com.meteo.meteo.controlers;

import com.meteo.meteo.entities.User;
import com.meteo.meteo.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/users")
public class UserControler {
    @Autowired
    private UserInterface userRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
    @GetMapping("/save")
    public ResponseEntity saveNewUser(
            @RequestBody User newUser) {
       userRepository.save(newUser);
        System.out.println(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("getById/{id}")
    public User getById(@PathVariable("id")long id)
    {
       return userRepository.getUserByIdUser(id);
    }

}
