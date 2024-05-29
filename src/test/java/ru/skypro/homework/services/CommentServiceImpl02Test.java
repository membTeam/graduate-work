package ru.skypro.homework.services;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.skypro.homework.dto.CommentAdd;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.enitities.Advertisement;
import ru.skypro.homework.enitities.CommentEnt;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;


@SpringBootTest
public class CommentServiceImpl02Test {

    @MockBean
    private CommentRepository commentRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private AdvertisementRepository advertisementRepo;

    @MockBean
    private UserUtils userUtils;


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
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));
        when(commentRepo.save(any(CommentEnt.class))).thenReturn(comment);
        when(advertisementRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(adv));

        var result = commentService.addComment(1, commentAdd);

        assertTrue(result.RESULT);
        assertEquals("text comment", result.getValue().getText());

    }

    @Test
    public void updateCommentForId() {

        var newText = "new text commentEnt";

        var user = User.builder()
                .id(1)
                .role(Role.ADMIN)
                .build();

        var commentEnt = CommentEnt.builder()
                .id(1)
                .userId(user.getId())
                .adId(1)
                .text("text commentEnt")
                .build();

        var commentEntSave = CommentEnt.builder()
                .id(commentEnt.getId())
                .userId(commentEnt.getUserId())
                .adId(commentEnt.getAdId())
                .text(newText)
                .build();

        var commentUpdate = CommentAdd.builder()
                .text(newText)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));
        when(commentRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(commentEnt));
        when(commentRepo.save(any(CommentEnt.class))).thenReturn(commentEntSave);
        when(userRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(user));

        var result = commentService.updateCommentForId(1, commentUpdate);

        assertTrue(result.RESULT);

        var data = result.getValue();
        assertEquals(newText, data.getText());
    }

    @Test
    public void updateCommentForId_equalText() {

        var newText = "text commentEnt";

        var user = User.builder()
                .id(1)
                .role(Role.ADMIN)
                .build();

        var commentEnt = CommentEnt.builder()
                .id(1)
                .userId(user.getId())
                .adId(1)
                .text("text commentEnt")
                .build();

        var commentEntSave = CommentEnt.builder()
                .id(commentEnt.getId())
                .userId(commentEnt.getUserId())
                .adId(commentEnt.getAdId())
                .text(newText)
                .build();

        var commentUpdate = CommentAdd.builder()
                .text(newText)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));
        when(commentRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(commentEnt));
        when(userRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(user));

        var result = commentService.updateCommentForId(1, commentUpdate);

        assertFalse(result.RESULT);

    }

    @Test
    public void updateCommentForId_notPermites() {

        var newText = "new text commentEnt";

        var user = User.builder()
                .id(1)
                .role(Role.USER)
                .build();

        var commentEnt = CommentEnt.builder()
                .id(1)
                .userId(2)
                .adId(1)
                .text("text commentEnt")
                .build();

        var commentEntSave = CommentEnt.builder()
                .id(commentEnt.getId())
                .userId(commentEnt.getUserId())
                .adId(commentEnt.getAdId())
                .text(newText)
                .build();

        var commentUpdate = CommentAdd.builder()
                .text(newText)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));
        when(commentRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(commentEnt));
        when(userRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(user));

        var result = commentService.updateCommentForId(1, commentUpdate);

        assertFalse(result.RESULT);

    }

    @Test
    public void deleteComment() {
        var user = User.builder()
                .id(1)
                .role(Role.ADMIN)
                .build();

        var commentEnt = CommentEnt.builder()
                .userId(user.getId())
                .adId(1)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));
        when(commentRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(commentEnt));
        doNothing().when(commentRepo).deleteById(any(Integer.class));

        var result = commentService.deleteComment(1, 1);

        assertTrue(result.RESULT);
    }

    @Test
    public void deleteComment_notPermites() {
        var user = User.builder()
                .id(1)
                .role(Role.USER)
                .build();

        var commentEnt = CommentEnt.builder()
                .userId(2)
                .adId(1)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));
        when(commentRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(commentEnt));
        doNothing().when(commentRepo).deleteById(any(Integer.class));

        var result = commentService.deleteComment(1, 1);

        assertFalse(result.RESULT);
    }

}
