package ru.skypro.homework.enitities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Класс предназначен для хранения аватарок
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"image"}))
public class UserAvatar {
    @Id
    private Integer id;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "media_type", columnDefinition = "varchar(100)")
    private String mediaType;

    @Column(columnDefinition = "varchar(150)")
    private String image;

    private byte[] data;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    @Setter
    private User user;
}
