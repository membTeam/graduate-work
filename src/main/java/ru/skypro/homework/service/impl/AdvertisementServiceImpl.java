package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.enitities.Advertisement;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdvertisementService;

@Log4j
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl  implements AdvertisementService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advRepository;

    @Override
    public boolean addAdv(Adv adv) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer userId = userRepository.getUserIdByUsername(userDetails.getUsername());

        if (userId < 0) {
            return false;
        }

        Advertisement advertisement = Advertisement.builder()
                .price(adv.getPrice())
                .title(adv.getTitle())
                .userId(userId)
                .image("")
                .build();

        try {
            advRepository.save(advertisement);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
