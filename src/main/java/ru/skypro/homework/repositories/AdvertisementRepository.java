package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.enitities.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

}
