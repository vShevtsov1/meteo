package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.LoginDTO;
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

    private ModelMapper modelMapper;
    private UserServices userServices;

    public UserControler(ModelMapper modelMapper, UserServices userServices) {
        this.modelMapper = modelMapper;
        this.userServices = userServices;
    }

    @Operation(summary = "Get all users")
    @GetMapping()
    public @ResponseBody Iterable<UserDTO> getAllUsers() {
        return userServices.getAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save new user")
    @PostMapping("/save")
    public ResponseEntity saveNewUser(
            @Parameter(description = "json body with info about user") @RequestBody User newUser) {
        userServices.save(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Save new user")
    @GetMapping("id/{id}")
    public UserDTO getById( @Parameter(description = "id of user to be searched")@PathVariable("id")long id)
    {
       return modelMapper.map(userServices.getUserByIdUser(id), UserDTO.class);
    }

    @Operation(summary = "login a user into application")
    @PostMapping("/login")
    public Object LoginUser(@RequestBody LoginDTO loginDTO) {
        return userServices.login(loginDTO);
    }
    @Operation(summary = "Get user by e-mail")
    @GetMapping("email/{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email)
    {
        return modelMapper.map(userServices.getUserByMail(email), UserDTO.class);
    }
    @Operation(summary = "CHeck token validity")
    @GetMapping(path = "valid/{toke}")
    public Boolean validateToken(@Parameter(description = "token to be checked")@PathVariable("token") String token)
    {
        return userServices.validateToken(token);
    }

}
