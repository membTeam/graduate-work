package ru.skypro.homework.enitities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}
