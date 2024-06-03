package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Класс добавления/изменения комментария
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentAdd {
    private String text;
}
