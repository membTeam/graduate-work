package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;

import ru.skypro.homework.enitities.Advertisement;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;

import javax.transaction.Transactional;
import java.util.stream.Collectors;


@Log4j
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl  implements AdvertisementService {

    private final AdvertisementRepository advRepository;
    private final UserUtils userUtils;


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
        var fileName = String.format("adv-%d-%s", user.getId(), image.getOriginalFilename().substring(0, lastIndexOf));

        try {
            var advertisement = Advertisement.builder()
                    .price(adv.getPrice())
                    .title(adv.getTitle())
                    .userId(user.getId())
                    .image(fileName)
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
    public ValueFromMethod<Ads> myAd() {
        ValueFromMethod<User> fromUserUtils = userUtils.getUserByUsername();
        if (!fromUserUtils.RESULT) {
            log.error(fromUserUtils.MESSAGE);
            return new ValueFromMethod(false, fromUserUtils.MESSAGE);
        }

        User user = fromUserUtils.VALUE;
        var lsAdvImg =  advRepository.findAllAdv(user.getId());

        var result = lsAdvImg.stream().map(item -> Ad.builder()
                    .author(user.getId())
                    .pk(item.getId())
                    .price(item.getPrice())
                    .title(item.getTitle())
                    .image(item.getImage())
                    .build()
        ).collect(Collectors.toList());

        Ads ads = Ads.builder()
                .count(result.size())
                .results(result)
                .build();

        return new ValueFromMethod<>(ads);
    }
}
