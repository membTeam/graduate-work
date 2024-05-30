package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.enitities.UserAvatar;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Integer> {
        UserAvatar findByImage(String image);

        @Query(value = "select exists(select * from user_avatar where id = :id)", nativeQuery = true)
        boolean existsUserAvatar(Integer id);

        @Query(value = "select exists(select * from user_avatar where image = :image)", nativeQuery = true)
        boolean existsImgAvatar(String image);

}
