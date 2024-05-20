package ru.skypro.homework.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdvRepositoryTest {
    @Autowired
    private AdvertisementRepository advertisementRepo;


    @Test
    public void findByImage() {
        var res = advertisementRepo.findByImage("img-1422027750-smartfon");

        assertTrue(res.isPresent());
    }
}
