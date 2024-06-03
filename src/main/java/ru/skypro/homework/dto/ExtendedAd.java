package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**<pre>данные, связынных с объявлением
 * Развернутая структура
 * используется в AdvertisementServiceImpl
 * </pre>

 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtendedAd {

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
