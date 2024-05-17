package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.utils.ValueFromMethod;

import javax.transaction.Transactional;

public interface AdvertisementService {

    @Transactional
    boolean addAd(Adv adv, MultipartFile photo);

    ValueFromMethod myAd();

/*    @Transactional
    boolean addAd(Adv adv);*/
}
