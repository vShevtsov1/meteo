package com.meteo.meteo.services;

import com.meteo.meteo.Constants;
import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.DTO.LoginDTO;
import com.meteo.meteo.DTO.RegisterDTO;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Activation activation;
    private TokenServices tokenServices;

    public UserServices(UserRepository userRepository, Activation activation, TokenServices tokenServices) {
        this.userRepository = userRepository;
        this.activation = activation;
        this.tokenServices = tokenServices;
    }

    public JwtDTO login(LoginDTO loginDTO) {
        User userlogin = userRepository.getUserForLogin(loginDTO.getEmail());
        if (!passwordEncoder.matches(loginDTO.getPassword(), userlogin.getPassword()) || userlogin == null || userlogin.getActive() == false) {
            return null;
        }
        {
            return new JwtDTO(tokenServices.generateTokenUser(userlogin));
        }
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getUserByIdUser(long id) {
        return userRepository.getUserByIdUser(id);
    }

    public User getUserByMail(String login) {
        return userRepository.getUserByMail(login);
    }

    public User save(RegisterDTO registerDTO) {
        User user = userRepository.save(new User(registerDTO.getName(), registerDTO.getSurname(), registerDTO.getDateOfBirth()
                , registerDTO.getEmail(),Constants.user.toString(), passwordEncoder.encode(registerDTO.getPassword()), false));
        activation.sendEmail(tokenServices.activationToken(user.getMail()));
        return user;
    }

    public void update(long id) {
        userRepository.updateUserActivation(id);
    }

    public ResponseEntity changeActivation(String token) {
        if (tokenServices.validateToken(token)) {
            User user = getUserByMail(tokenServices.getMail(token));
            update(user.getIdUser());
            return ResponseEntity.ok("Account status for user " + user.getMail() + " completely changed");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

