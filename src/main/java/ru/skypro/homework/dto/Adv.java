package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Adv implements Serializable {
    private String title;
    private int price;
    private String description;
    private MultipartFile image;
}
