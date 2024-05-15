package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Builder
public class CreateOrUpdateAd {
    @NotBlank
    @Size(min = 4, max = 32, message = "Размер не менее 4 и не более 32 символов")
    private String title;

    @Size(min = 0, max = 10000000, message = "от нуля до 10000000")
    private int price;

    @NotBlank
    @Size(min = 8, max = 64, message = "Размер не менее 8 и не более 64 символов")
    private String description;

}
