package ru.skypro.homework.enitities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "comment")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    private long createdAt;

    @NotBlank
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    User user;
}
