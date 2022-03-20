package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.UserDTO;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.interfaces.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.meteo.meteo.services.UserServices;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/users")
public class UserControler {


    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserServices userServices;

    public UserControler(UserRepository userRepository, ModelMapper modelMapper, UserServices userServices) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userServices = userServices;
    }

    @GetMapping()
    public @ResponseBody Iterable<UserDTO> getAllUsers() {
        return userRepository.getAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
    @PostMapping("/save")
    public ResponseEntity saveNewUser(
            @RequestBody User newUser) {
       userRepository.save(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public UserDTO getById(@PathVariable("id")long id)
    {
       return modelMapper.map(userRepository.getUserByIdUser(id), UserDTO.class);
    }

    @GetMapping("/login/{login}/{password}")
    public Object LoginUser(@PathVariable("login")String login,@PathVariable("password") String password) {
        return userServices.login(login,password);
    }
    @GetMapping("email/{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email)
    {
        return modelMapper.map(userRepository.getUserByMail(email), UserDTO.class);
    }

}
