package ru.skypro.homework.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.skypro.homework.dto.CommentAdd;
import ru.skypro.homework.enitities.Advertisement;
import ru.skypro.homework.enitities.CommentEnt;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImpl02Test {

    @MockBean
    private CommentRepository commentRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private AdvertisementRepository advertisementRepo;


    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void addComment() {

        var user = User.builder()
                .id(1)
                .build();

        var comment = CommentEnt.builder()
                .userId(user.getId())
                .adId(1)
                .text("text comment")
                .build();

        var commentAdd = CommentAdd.builder()
                .text("text comment")
                .build();

        var adv = Advertisement.builder()
                .id(1)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(commentRepo.save(any(CommentEnt.class))).thenReturn(comment);
        when(advertisementRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(adv));

        var result = commentService.addComment(1, commentAdd);

        assertTrue(result.RESULT);
        assertEquals("text comment", result.getValue().getText());

    }

    @Test
    public void updateCommentForId() {
        var user = User.builder()
                .id(1)
                .build();

        var commentEnt = CommentEnt.builder()
                .userId(user.getId())
                .adId(1)
                .text("text commentEnt")
                .build();

        var commentCurrent = CommentAdd.builder()
                .text("text commentEnt")
                .build();

        var commentSave = CommentEnt.builder()
                .userId(user.getId())
                .adId(1)
                .text("new text commentEnt")
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(commentRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(commentEnt));
        when(commentRepo.save(any(CommentEnt.class))).thenReturn(commentSave);
        when(userRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(user));

        var result = commentService.updateCommentForId(1, commentCurrent);

        assertTrue(result.RESULT);
        assertEquals("new text commentEnt", result.getValue().getText());
    }

    @Test
    public void deleteComment() {
        var user = User.builder()
                .id(1)
                .build();

        var commentEnt = CommentEnt.builder()
                .userId(user.getId())
                .adId(1)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(commentRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(commentEnt));
        doNothing().when(commentRepo).deleteById(any(Integer.class));

        var result = commentService.deleteComment(1, 1);

        assertTrue(result.RESULT);
    }

}
