package ru.skypro.homework.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.UserAvatarRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceImpl02Test {

    @Autowired
    private UserAvatarRepository userAvatarRepo;

    @Test
    public void existsImageAvatar() {

        Integer id = 1;
        var hashId = id.toString().hashCode();
        var strImage = String.format("/img/avatar/avatar-%d", hashId);;

        var res = userAvatarRepo.existsImgAvatar(strImage);

        assertTrue(res);
    }


}
