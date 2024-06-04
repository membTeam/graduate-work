package ru.skypro.homework.service;

import ru.skypro.homework.dto.WebResultPhoto;

/**
 * Интереейс сервиса аватарок и фотографий объявлений
 */
public interface ImgControllerService {

    WebResultPhoto getPhotoAdv(String image);

    WebResultPhoto getImageAvatar(String image);
}
