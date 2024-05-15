package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Comments {
    private int count;
    private List<Comment> results;

}
