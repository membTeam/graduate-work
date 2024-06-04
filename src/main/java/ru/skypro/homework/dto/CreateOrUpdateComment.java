package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateComment {

    @NotBlank
    @Size(min = 8, max = 64, message = "Размер не менее 8 и не более 64 символов")
    private String text;
}
