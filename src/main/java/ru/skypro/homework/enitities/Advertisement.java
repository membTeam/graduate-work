package ru.skypro.homework.enitities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Size(min = 0, max = 100000, message = "от нуля до 10000000")
    private int price;

    @Column(columnDefinition = "varchar(150)")
    private String image;

    @Column(columnDefinition = "varchar(250)")
    private String title;

    @Column(columnDefinition = "varchar(500)")
    private String description;

    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    User user;

}
