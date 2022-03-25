package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.UserDTO;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.interfaces.UserRepository;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

    @Operation(summary = "Get all users")
    @GetMapping()
    public @ResponseBody Iterable<UserDTO> getAllUsers() {
        return userRepository.getAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save new user")
    @PostMapping("/save")
    public ResponseEntity saveNewUser(
            @Parameter(description = "json body with info about user") @RequestBody User newUser) {
        userRepository.save(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Save new user")
    @GetMapping("id/{id}")
    public UserDTO getById( @Parameter(description = "id of user to be searched")@PathVariable("id")long id)
    {
       return modelMapper.map(userRepository.getUserByIdUser(id), UserDTO.class);
    }

    @Operation(summary = "login a user into application")
    @GetMapping("/login/{login}/{password}")
    public Object LoginUser(@PathVariable("login")String login,@PathVariable("password") String password) {
        return userServices.login(login,password);
    }
    @Operation(summary = "Get user by e-mail")
    @GetMapping("email/{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email)
    {
        return modelMapper.map(userRepository.getUserByMail(email), UserDTO.class);
    }
    @Operation(summary = "CHeck token validity")
    @GetMapping(path = "valid/{toke}")
    public Boolean validateToken(@Parameter(description = "token to be checked")@PathVariable("token") String token)
    {
        return userServices.validateToken(token);
    }

    @GetMapping("test")
    public void test()
    {
        String claims = userServices.getUserRole("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IjEiLCJzdXJuYW1lIjoiMSIsInJvbGUiOiIxIiwiZXhwIjoxNjQ4MDQ3MTQ5fQ.ST6CbZGa7XP6wU27kgJGg-f4xUk6kVMn8Y0x7UvEILU");
        System.out.println(claims);
    }
}
