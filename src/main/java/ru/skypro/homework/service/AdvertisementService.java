package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.utils.ValueFromMethod;

import javax.transaction.Transactional;

public interface AdvertisementService {

    boolean updateImageAd(Integer id, MultipartFile image);

    @Transactional
    boolean addAd(Adv adv, MultipartFile photo);

    ValueFromMethod myAd();

    boolean updateAd(Integer advId, Adv ad);

    boolean deleteAd(Integer advId);

    ValueFromMethod allAd();


/*    @Transactional
    boolean addAd(Adv adv);*/
}
