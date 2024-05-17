package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDTO {
    private Integer id;

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private String image;
}
