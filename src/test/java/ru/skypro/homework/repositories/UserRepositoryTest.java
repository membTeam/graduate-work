package ru.skypro.homework.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername() {
        var user = userRepository.findByUsername("usermail@mail.ru");

        assertNotNull(user);
    }


    @Test
    public void getImageAvatar() {
        var res = userRepository.getImageAvatar(1);

        assertNotNull(res);
    }

    @Test
    public void getUserIdByUsername() {
        var res = userRepository.getUserIdByUsername("usermail@mail.ru");

        assertTrue(res>0);
    }

    @Test
    public void getUserIdByUsername_notData() {
        var res = userRepository.getUserIdByUsername("nik");

        assertTrue(res<0);
    }

}
