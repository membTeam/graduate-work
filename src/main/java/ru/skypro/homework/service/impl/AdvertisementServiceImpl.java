package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;

import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.enitities.Advertisement;
import ru.skypro.homework.enitities.AdvertisementImg;
import ru.skypro.homework.repositories.AdvertisementImgRepository;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.enitities.User;

@Log4j
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl  implements AdvertisementService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advRepository;
    private final AdvertisementImgRepository advertisementImgRepo;

    @Override
    @Transactional
    public boolean addAdv(Adv adv) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user = userRepository.findByUsername(userDetails.getUsername());
        if (user == null) {
            return false;
        }

        var photo = adv.getImage();
        var fileName = String.format("adv-%d-%s", user.getId(), photo.getOriginalFilename());

        AdvertisementImg advertisementImg = null;

        var advertisement = Advertisement.builder()
                .price(adv.getPrice())
                .title(adv.getTitle())
                .userId(user.getId())
                .image(fileName)
                .user(user)
                .build();

        try {
            advertisementImg = AdvertisementImg.builder()
                    .fileSize(photo.getSize())
                    .mediaType(photo.getContentType())
                    .data(photo.getBytes())
                    .build();
        } catch (IOException ex) {
            log.error("addAdv: " + ex.getMessage());
            return false;
        }

        try {
            var resSave = advRepository.save(advertisement);
            advertisementImg.setAdvertisement(resSave);

            advertisementImgRepo.save(advertisementImg);

            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }


    }
}
