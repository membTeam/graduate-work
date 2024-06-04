package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**<pre>класс обновления/добавления объявления в endPoint
 * </pre>
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class CreateOrUpdateAd implements Serializable {
    @NotBlank(message = "Не заполнено поле заголовок")
    @Size(min = 4, max = 32, message = "Размер не менее 4 и не более 32 символов")
    private String title;

    @Size(min = 0, max = 10000000, message = "от нуля до 10000000")
    private int price;

    @NotBlank(message = "Не заполнено поле описания")
    @Size(min = 8, max = 64, message = "Размер не менее 8 и не более 64 символов")
    private String description;

}
