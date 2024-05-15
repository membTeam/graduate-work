package ru.skypro.homework.enitities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "Extended_Ad")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;

    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private int price;
    private String title;
}
