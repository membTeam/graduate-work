package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;

import ru.skypro.homework.dto.Role;
import ru.skypro.homework.enitities.Advertisement;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Log4j
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl  implements AdvertisementService {

    private final AdvertisementRepository advRepository;
    private final UserUtils userUtils;


    private ValueFromMethod initResultError(String strErr) {
        log.error(strErr);
        return new ValueFromMethod(false, strErr);
    }

    private ValueFromMethod<Advertisement> verifyUser(Integer advId) {

        ValueFromMethod<User> resFromUserUtils = userUtils.getUserByUsername();

        if (!resFromUserUtils.RESULT) {
            return initResultError(resFromUserUtils.MESSAGE);
        }

        var resFind = advRepository.findById(advId);
        if (!resFind.isPresent()) {
            return initResultError("updateImageAd: Объявление не найдено");
        }

        var adv = resFind.orElseThrow();

        if (resFromUserUtils.VALUE.getRole() == Role.USER && !adv.getUserId().equals(resFromUserUtils.VALUE.getId())) {
            return initResultError("updateImageAd: Только автор объявления может вносить изменения");
        }

        return new ValueFromMethod(adv);
    }

    private Ads initAds(List<Advertisement> lsAdv) {
        var result = lsAdv.stream().map(item -> Ad.builder()
                .author(item.getUserId())
                .pk(item.getId())
                .price(item.getPrice())
                .title(item.getTitle())
                .image(item.getImage())
                .build()
        ).collect(Collectors.toList());

        return Ads.builder()
                .count(result.size())
                .results(result)
                .build();
    }


    @Override
    public boolean deleteAd(Integer advId) {
        var resVerifyUser = verifyUser(advId);

        if (!resVerifyUser.RESULT) {
            return false;
        }

        advRepository.deleteById(advId);
        return true;
    }

    @Override
    public boolean updateAd(Integer advId, Adv ad) {

        var resVerifyUser = verifyUser(advId);

        if (!resVerifyUser.RESULT) {
            return false;
        }

        var adv = resVerifyUser.VALUE;

        adv.setTitle(ad.getTitle());
        adv.setPrice(ad.getPrice());
        adv.setDescription(ad.getDescription());

        advRepository.save(adv);

        return true;
    }

    @Override
    public boolean updateImageAd(Integer advId, MultipartFile image) {

        var resVerifyUser = verifyUser(advId);

        if (!resVerifyUser.RESULT) {
            return false;
        }

        try {
            resVerifyUser.VALUE.setData(image.getBytes());
            advRepository.save(resVerifyUser.VALUE);
            return true;

        } catch (Exception ex) {
            log.error("updateImageAd: " + ex.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean addAd(Adv adv, MultipartFile image) {

        ValueFromMethod<User> fromUserUtils = userUtils.getUserByUsername();
        if (!fromUserUtils.RESULT) {
            log.error(fromUserUtils.MESSAGE);
            return false;
        }

        User user = fromUserUtils.VALUE;

        var lastIndexOf = image.getOriginalFilename().lastIndexOf('.');
        var hashName = String.format("adv-$d", user.getId()).hashCode();

        var fileName = String.format("%s-%s",
                hashName < 0 ? "/img/adv/img"+hashName : "/img/adv/image"+ hashName,
                image.getOriginalFilename().substring(0, lastIndexOf));

        try {
            var advertisement = Advertisement.builder()
                    .price(adv.getPrice())
                    .title(adv.getTitle())
                    .userId(user.getId())
                    .image(fileName)
                    .size(image.getSize())
                    .metaData(image.getContentType())
                    .description(adv.getDescription())
                    .data(image.getBytes())
                    .user(user)
                    .build();

            advRepository.save(advertisement);
            return true;
        } catch (Exception ex) {
            log.error("addDev: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public ValueFromMethod allAd() {

        var ads = initAds(advRepository.findAll());

        return new ValueFromMethod(ads);
    }

    @Override
    public ValueFromMethod<Ads> myAd() {
        ValueFromMethod<User> fromUserUtils = userUtils.getUserByUsername();
        if (!fromUserUtils.RESULT) {
            log.error(fromUserUtils.MESSAGE);
            return new ValueFromMethod(false, fromUserUtils.MESSAGE);
        }

        User user = fromUserUtils.VALUE;
        Ads ads = initAds(advRepository.findAllAdv(user.getId()));

        return new ValueFromMethod<>(ads);
    }

}
