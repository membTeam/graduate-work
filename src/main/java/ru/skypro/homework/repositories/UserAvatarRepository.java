package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.enitities.UserAvatar;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Integer> {
        UserAvatar findByImage(String image);
}
