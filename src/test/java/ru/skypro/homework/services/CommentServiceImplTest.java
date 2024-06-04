package ru.skypro.homework.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void getCommentsByAdvId() {
        var userDef = commentRepo.findAll().stream().collect(Collectors.toList()).get(0);

        var result = commentService.getCommentsByAdvId(userDef.getAdId());

        assertTrue(result.RESULT);

        var data = result.getValue();
        assertTrue(data.getCount()>0);
    }

}
