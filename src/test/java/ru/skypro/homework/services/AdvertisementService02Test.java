package ru.skypro.homework.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.service.AdvertisementService;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AdvertisementService02Test {

    @Autowired
    private AdvertisementRepository advertisementRepo;

    @Autowired
    private AdvertisementService advertisementServ;

    @Test
    public void detailsAd() {
        var firstAd = advertisementRepo.findAll().get(0);

        var res = advertisementServ.detailsAd(firstAd.getId());

        assertTrue(res.RESULT);
        assertInstanceOf(ExtendedAd.class, res.getValue());

    }

}
