package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@Builder
public class Comment {
    private int pk;
    private int author;
    private String authorImage;
    private String authorFirstName;
    private long createdAt;
    private String text;
}
