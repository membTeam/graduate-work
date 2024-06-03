package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skypro.homework.dto.Register;

/**
 * Интерфейс сервиса ауторизации
 */
public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);

    boolean existUser(String username);
    UserDetailsService loadUserDetailsService(String username);
}
