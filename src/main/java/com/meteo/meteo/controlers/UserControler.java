package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.userDTO;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.interfaces.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.meteo.meteo.services.userServices;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/users")
public class UserControler {


    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private userServices userServices;

    public UserControler(UserRepository userRepository, ModelMapper modelMapper, com.meteo.meteo.services.userServices userServices) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userServices = userServices;
    }

    @GetMapping()
    public @ResponseBody Iterable<userDTO> getAllUsers() {
        return userRepository.getAll().stream().map(user -> modelMapper.map(user, userDTO.class)).collect(Collectors.toList());
    }
    @PostMapping("/save")
    public ResponseEntity saveNewUser(
            @RequestBody User newUser) {
       userRepository.save(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public userDTO getById(@PathVariable("id")long id)
    {
       return modelMapper.map(userRepository.getUserByIdUser(id), userDTO.class);
    }

    @GetMapping("/login/{login}/{password}")
    public Object LoginUser(@PathVariable("login")String login,@PathVariable("password") String password) {
        User userlogin = null;
        userlogin = userRepository.getUserByMailAndPassword(login,password);
        if(userlogin==null)
        {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        else {
            return userServices.generateToken(login);
        }
    }
    @GetMapping("email/{email}")
    public userDTO getUserByEmail(@PathVariable("email") String email)
    {
        return modelMapper.map(userRepository.getUserByMail(email), userDTO.class);
    }

}
