package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.userDTO;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.interfaces.UserRepository;
import org.modelmapper.ModelMapper;
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


    @GetMapping()
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    @PostMapping("/save")
    public ResponseEntity saveNewUser(
            @RequestBody User newUser) {
       userRepository.save(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("id/{id}")
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
    @GetMapping("email/{email}")
    public userDTO getUserByEmail(@PathVariable("email") String login)
    {
        return modelMapper.map(userRepository.getUserByMail(login), userDTO.class);
    }

}
