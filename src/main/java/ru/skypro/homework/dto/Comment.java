package ru.skypro.homework.dto;


import lombok.*;

/**
 <pre>Класс комментария.
    Используется в интерфейсе CommentService
    в качестве возвращаемого результата
 </pre>
 */
@Getter
@AllArgsConstructor
@Builder
public class Comment {
    private int pk;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    @Setter
    private String text;
}
