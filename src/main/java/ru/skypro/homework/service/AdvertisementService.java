package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.Adv;

public interface AdvertisementService {
    boolean addAdv(Adv adv);

}
