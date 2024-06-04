package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.utils.ValueFromMethod;

import javax.transaction.Transactional;


/**
 * Интерфейс сервиса объявлений
 */
public interface AdvertisementService {

    boolean updateImageAd(Integer id, MultipartFile image);

    @Transactional
    boolean addAd(CreateOrUpdateAd adv, MultipartFile photo);

    ValueFromMethod myAd();

    boolean updateAd(Integer advId, CreateOrUpdateAd ad);

    boolean deleteAd(Integer advId);

    ValueFromMethod allAd();

    ValueFromMethod detailsAd(Integer advId);

}
