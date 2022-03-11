package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.userDTO;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.interfaces.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/users")
public class UserControler {


    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserControler(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


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

    @GetMapping("/login/{login}/{password}")
    public ResponseEntity LoginUser(@PathVariable("login")String login,@PathVariable("password") String password) {
        User userlogin = null;
        userlogin = userRepository.getUserByMailAndPassword(login,password);
        if(userlogin==null)
        {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        else {
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }
    @GetMapping("getuserbyemail/{email}")
    public userDTO getUserByEmail(@PathVariable("email") String login)
    {
        return modelMapper.map(userRepository.getUserByMail(login), userDTO.class);
    }

}
