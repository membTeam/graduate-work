package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Класс регистрации пользователя
 */
@Getter
@Builder
@AllArgsConstructor
@NotBlank
public class Register {

    @NotBlank
    @Size(min = 4, max = 32, message = "Размер не менее 4 и не более 32 символов")
    private String username;

    @NotBlank
    @Size(min = 8, max = 16, message = "Размер не менее 8 и не более 16 символов")
    private String password;

    @NotBlank
    @Size(min = 2, max = 16, message = "Размер не менее 2 и не более 16 символов")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 16, message = "Размер не менее 2 и не более 16 символов")
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    private Role role;
}
