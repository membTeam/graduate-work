package ru.skypro.homework.dto;


import lombok.*;


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
