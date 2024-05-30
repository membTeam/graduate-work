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
import ru.skypro.homework.repositories.CommentRepository;
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
    private final CommentRepository commentRepository;

    private final AdvertisementRepository advRepository;
    private final UserUtils userUtils;


    private ValueFromMethod<Advertisement> verifyUser(Integer advId) {

        try {

            ValueFromMethod<User> resFromUserUtils = userUtils.getUserByUsername();

            if (!resFromUserUtils.RESULT) {
                throw new Exception(resFromUserUtils.MESSAGE);
            }

            var resFindAdv = advRepository.findById(advId);
            if (!resFindAdv.isPresent()) {
                throw new IllegalArgumentException("updateImageAd: Объявление не найдено");
            }

            var adv = resFindAdv.orElseThrow();
            var userCurr = resFromUserUtils.getValue();

            if (!userCurr.getId().equals(adv.getUserId()) && !userCurr.getRole().equals(Role.ADMIN)) {
                throw new IllegalArgumentException("updateImageAd: Только автор объявления может вносить изменения");
            }

            return new ValueFromMethod(adv);

        } catch (Exception ex) {
            return ValueFromMethod.resultErr(ex.getMessage());
        }

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
    @Transactional
    public boolean deleteAd(Integer advId) {
        try {
            var resVerifyUser = verifyUser(advId);
            if (!resVerifyUser.RESULT) {
                throw new IllegalArgumentException(resVerifyUser.MESSAGE);
            }

            var lsComm = commentRepository.findByAdId(advId);
            if (lsComm.size() > 0) {
                commentRepository.deleteAll(lsComm);
            }

            advRepository.deleteById(advId);

            return true;

        } catch (Exception ex) {
            ValueFromMethod.resultErr("deleteAd: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAd(Integer advId, Adv ad) {

        var resVerifyUser = verifyUser(advId);

        if (!resVerifyUser.RESULT) {
            return false;
        }

        var adv = resVerifyUser.getValue();

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
    public ValueFromMethod<Ads> allAd() {

        Ads ads = initAds(advRepository.findAll());

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

        return new ValueFromMethod(ads);
    }

}
