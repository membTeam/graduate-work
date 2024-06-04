package ru.skypro.homework.repositories.userAvatarRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.utils.FileAPI;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserAvatarRepositoryTest {

    @Autowired
    private FileAPI fileAPI;

    @Test
    public void loadAvatar_default() throws IOException {
        var result = fileAPI.loadDefaultAvatar(-1);
        assertTrue(result.length>0);
    }

}
