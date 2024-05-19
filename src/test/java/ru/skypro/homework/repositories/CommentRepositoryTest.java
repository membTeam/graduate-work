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

    @Test
    public void getCommentsByUserId() {
        var res = commentRepo.getCommentsByUserId(1);

        assertNotNull(res);
    }

    @Test
    public void getCommentByUserId_forObj() {
        var lsComments = commentRepo.getCommentsByUserId(1);

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

        var resLs = commentRepo.getListCommentByAdId(1);

        assertTrue(resLs.size() > 0);
    }

}
