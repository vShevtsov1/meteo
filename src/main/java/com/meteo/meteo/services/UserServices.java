package com.meteo.meteo.services;

import com.meteo.meteo.DTO.JwtDTO;
import com.meteo.meteo.DTO.LoginDTO;
import com.meteo.meteo.DTO.RegisterDTO;
import com.meteo.meteo.entities.Roles;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationService activationService;
    private final TokenServices tokenServices;

    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder, ActivationService activationService, TokenServices tokenServices) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activationService = activationService;
        this.tokenServices = tokenServices;
    }

    public JwtDTO login(LoginDTO loginDTO) {
        User userlogin = userRepository.getUserForLogin(loginDTO.getEmail());
        if (!passwordEncoder.matches(loginDTO.getPassword(), userlogin.getPassword()) || !userlogin.getActive())
            return null;

        return new JwtDTO(tokenServices.generateTokenUser(userlogin));
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getUserByMail(String login) {
        return userRepository.getUserByMail(login);
    }

    public User save(RegisterDTO registerDTO) {
        User user = userRepository.save(new User(registerDTO.getName(), registerDTO.getSurname(), registerDTO.getDateOfBirth(),
                registerDTO.getEmail(), Roles.user, passwordEncoder.encode(registerDTO.getPassword()), false));
        activationService.sendEmail(tokenServices.activationToken(user.getMail()));
        return user;
    }

    public void update(long id) {
        userRepository.updateUserActivation(id);
    }

    public boolean changeActivation(String token) {
        if (tokenServices.validateToken(token)) {
            User user = getUserByMail(tokenServices.getMail(token));
            update(user.getIdUser());
            return true;
        } else {
            return false;
        }
    }
}

