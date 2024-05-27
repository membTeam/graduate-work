package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CommentAdd;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.enitities.CommentEnt;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Log4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepo;
    private final UserUtils userUtils;


    private ValueFromMethod initResultError(String strErr) {
        log.error(strErr);
        return new ValueFromMethod(false, strErr);
    }

    private Comment initComment(CommentEnt comment, User user) {
        return Comment.builder()
                .createdAt(comment.getCreatedAt())
                .text(comment.getText())
                .author(comment.getUserId())
                .authorFirstName(user.getFirstName())
                .pk(comment.getAdId())
                .authorImage(user.getAvatar() != null
                        ? user.getAvatar().getImage() : "empty")
                .build();
    }

    @Override
    public ValueFromMethod<Comments> getCommentsForId(Integer id) {

        var lsComments = commentRepo.getCommentsByAdvertisement(id);

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

        Comments comments = Comments.builder()
                .count(results.size())
                .results(results)
                .build();

        return new ValueFromMethod(comments);
    }

    @Override
    public ValueFromMethod<Comment> addComment(Integer adId, CommentAdd comment) {
        try {
            var ad = advertisementRepository.findById(adId).orElseThrow();
            var user = userRepo.findById(ad.getUserId()).orElseThrow();

            var now = LocalDateTime.now();
            long createdAt = now.toInstant(ZoneOffset.UTC).toEpochMilli();

            var comm = CommentEnt.builder()
                    .adId(ad.getId())
                    .userId(user.getId())
                    .createdAt(createdAt)
                    .text(comment.getText())
                    .build();

            var resSave = commentRepo.save(comm);
            var commRes = initComment(resSave, user);

            return new ValueFromMethod(commRes);

        } catch (Exception ex) {
            return initResultError("addComment " + ex.getMessage());
        }
    }

    @Override
    public ValueFromMethod<Comment> updateCommentForId(Integer id, CommentAdd commentAdd) {

        try {
            var commFind = commentRepo.findById(id).orElseThrow();
            var user = userRepo.findById(commFind.getUserId()).orElseThrow();

            commFind.setText(commentAdd.getText());

            var resSave = commentRepo.save(commFind);
            var commRes = initComment(resSave, user);

            return new ValueFromMethod(commRes);

        } catch (Exception ex) {
            return initResultError("updateCommentForId: " + ex.getMessage());
        }

    }

    @Override
    public ValueFromMethod<Comment> deleteComment(Integer adId, Integer commentId) {
        try {
            var commFind = commentRepo.findById(commentId).orElseThrow();
            var user = userUtils.getUserByUsername().VALUE;

            if (user == null) {
                throw new UsernameNotFoundException("Нет пользователя");
            }

            if ( !commFind.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN ) {
                throw new IllegalArgumentException("Только автор комментария или администратор могут удалить");
            }

            commentRepo.deleteById(commFind.getId());

            return new ValueFromMethod(true, "ok");

        } catch (Exception ex) {
            return initResultError("deleteComment: " + ex.getMessage());
        }
    }
}
