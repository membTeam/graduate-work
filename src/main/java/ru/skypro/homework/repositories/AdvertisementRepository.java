package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.enitities.Advertisement;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    @Query("From Advertisement a where a.userId = :id ")
    List<Advertisement> findAllAdv(Integer id);

    Optional<Advertisement> findByImage(String image);

}
