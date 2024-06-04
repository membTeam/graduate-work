package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**<pre>Структурированный класс комментариев
 * count кол-во комментариев
 * results список комментариев класса Ad
 * используется в реализации сервиса CommentService
 * </pre>
 */
@Getter
@AllArgsConstructor
@Builder
public class Comments {
    private int count;
    private List<Comment> results;

}
