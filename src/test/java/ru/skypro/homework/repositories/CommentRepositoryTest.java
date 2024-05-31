package ru.skypro.homework.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.Comment;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private AdvertisementRepository advertisementRepo;

    @Test
    public void getCommentsByUserId() {
        var res = commentRepo.getCommentsByAdvertisement(1);

        assertNotNull(res);
    }

    @Test
    public void getCommentByUserId_forObj() {

        var adId = commentRepo.findAll().get(0).getAdId();

        var lsComments = commentRepo.getCommentsByAdvertisement(adId);

        var results = lsComments.stream().map(item ->
                Comment.builder()
                        .author((Integer) item.get(0))
                        .pk((Integer) item.get(1))
                        .authorFirstName(item.get(2).toString())
                        .text(item.get(3).toString())
                        .createdAt(Long.parseLong(String.valueOf(item.get(4))))
                        .authorImage(item.get(5).toString())
                        .build()
        ).collect(Collectors.toList());

        assertTrue(results.size()>0);
    }


    @Test
    public void getListCommentByAdId() {

        var adId = commentRepo.findAll().get(0).getAdId();
        var resLs = commentRepo.getListCommentByAdId(adId);

        assertTrue(resLs.size() > 0);
    }

}
