package com.meteo.meteo.controlers;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.DTO.LoginDTO;
import com.meteo.meteo.DTO.RegisterDTO;
import com.meteo.meteo.DTO.UserDTO;
import com.meteo.meteo.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserServices userServices;

    public UserController(ModelMapper modelMapper, UserServices userServices) {
        this.modelMapper = modelMapper;
        this.userServices = userServices;
    }

    @Operation(summary = "Get all users")
    @GetMapping()
    public @ResponseBody
    Iterable<UserDTO> getAllUsers() {
        return userServices.getAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save new user")
    @PostMapping("/save")
    public ResponseEntity<UserDTO> saveNewUser(
            @Parameter(description = "json body with info about user") @RequestBody RegisterDTO registerDTO) {
        try {
            return ResponseEntity.ok(modelMapper.map(userServices.save(registerDTO), UserDTO.class));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Login a user into application")
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> LoginUser(@RequestBody LoginDTO loginDTO) {
        JwtDTO jwtDTO = userServices.login(loginDTO);
        if (jwtDTO != null) {
            return ResponseEntity.ok(jwtDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get user by e-mail")
    @GetMapping("/email")
    public UserDTO getUserByEmail(Authentication authentication) {
        return modelMapper.map(userServices.getUserByMail((String) authentication.getPrincipal()), UserDTO.class);
    }

    @GetMapping("/activation")
    public ResponseEntity<Object> activation(@RequestParam String token) {
        if (userServices.changeActivation(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
