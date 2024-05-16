package ru.skypro.homework.enitities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class AdvertisementImg {
    @Id
    private Integer id;

    @Column(name = "file_size")
    private int fileSize;

    @Column(name = "media_type", columnDefinition = "varchar(100)")
    private String mediaType;

    private byte[] data;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Advertisement advertisement;

}
