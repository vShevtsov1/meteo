package com.meteo.meteo.services;

import com.meteo.meteo.DTO.*;
import com.meteo.meteo.entities.Roles;
import com.meteo.meteo.entities.User;
import com.meteo.meteo.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.NoSuchProviderException;
import java.util.List;

@Service
public class UserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationService activationService;
    private final TokenServices tokenServices;
    private final ResetPasswordService resetPasswordService;

    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder, ActivationService activationService, TokenServices tokenServices, ResetPasswordService resetPasswordService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activationService = activationService;
        this.tokenServices = tokenServices;
        this.resetPasswordService = resetPasswordService;
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

    public User save(RegisterDTO registerDTO) throws NoSuchProviderException {
        User user = userRepository.save(new User(registerDTO.getName(), registerDTO.getSurname(), registerDTO.getDateOfBirth(),
                registerDTO.getEmail(), Roles.user, passwordEncoder.encode(registerDTO.getPassword()), false));
        activationService.sendEmail(tokenServices.activationToken(user.getMail()));
        return user;
    }

    public void update(long id) {
        userRepository.updateUserActivation(id);
    }

    public void updatePassword(long id, String password) {
        userRepository.updateUserResetPassword(id, password);
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

    public Boolean resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, String token) {
        if (tokenServices.validateToken(token)) {
            User user = getUserByMail(tokenServices.getMail(token));
            updatePassword(user.getIdUser(), passwordEncoder.encode(resetPasswordDTO.getPassword()));
            return true;
        } else {
            return false;
        }
    }

    public Boolean changedPassword(@RequestBody ChangePasswordDTO changePasswordDTO, String token) {
        if (tokenServices.validateToken(token)) {
            User user = getUserByMail(tokenServices.getMail(token));
            if(passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.getPassword()))
            {
                updatePassword(user.getIdUser(), passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            }
            else {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public void sendEmailForChangingPassword(String email) throws NoSuchProviderException {
        String token = tokenServices.activationToken(email);
        resetPasswordService.sendEmail(token);
    }
}

