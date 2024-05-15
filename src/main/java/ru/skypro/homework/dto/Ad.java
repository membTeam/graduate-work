package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Getter
@AllArgsConstructor
@Builder
public class Ad {
    private int pk;
    private int author;
    private String image;
    private int price;
    private String title;
}
