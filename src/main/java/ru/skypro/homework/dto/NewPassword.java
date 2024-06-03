package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * Класс изменения пароля
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewPassword {

    @NotBlank
    @Size(min = 8, max = 16, message = "Размер не менее 8 и не более 16 символов")
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 16, message = "Размер не менее 8 и не более 16 символов")
    private String newPassword;
}
