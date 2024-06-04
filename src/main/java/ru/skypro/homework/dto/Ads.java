package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


/**<pre>Структурированный класс объявлений
 * count кол-во объявлений
 * results список объявлений класса Ad
 * </pre>
 */
@Getter
@Builder
@AllArgsConstructor
public class Ads {
    private int count;
    private List<Ad> results;
}
