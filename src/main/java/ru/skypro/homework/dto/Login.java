package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс ауторизации пользователя
 */
@Data
public class Login {

    private String username;
    private String password;
}
