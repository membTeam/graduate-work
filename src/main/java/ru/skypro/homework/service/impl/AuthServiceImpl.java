package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;


    @Override
    public boolean login(String userName, String password) {
        if (!userRepo.userExists(userName)) {
            return false;
        }

        var userDetails = userRepo.findByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        /*if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())
                        .build());*/
        if (userRepo.userExists(register.getUsername())) {
            return false;
        }

        var user = User.builder()
                .password(this.encoder.encode(register.getPassword()))
                .email(register.getUsername())
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .role(register.getRole())
                .phone(register.getPhone())
                .build();

        try {
            userRepo.save(user);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    @Override
    public boolean existUser(String username) {
        return userRepo.userExists(username);
    }

    @Override
    public UserDetailsService loadUserDetailsService(String username) {
        return (UserDetailsService) userRepo.findByUsername(username);
    }

}
