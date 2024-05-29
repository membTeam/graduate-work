package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.CommentEvent;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CommentAdd;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.enitities.CommentEnt;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.enitities.UserAvatar;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.repositories.UserAvatarRepository;
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
    private final UserAvatarRepository userAvatarRepo;
    private final UserUtils userUtils;


    private Comment initComment(CommentEnt comment, User user) {
            var userAvatar = userAvatarRepo.findById(user.getId());
            var image = userAvatar.isEmpty() ? "/img/avatar/empty" : userAvatar.orElseThrow().getImage();

            return Comment.builder()
                    .createdAt(comment.getCreatedAt())
                    .text(comment.getText())
                    .author(comment.getUserId())
                    .authorFirstName(user.getFirstName())
                    .pk(comment.getAdId())
                    .authorImage(image)
                    .build();
    }

    private ValueFromMethod<CommentEnt> verifyUser(Integer commentId) {

        try {
            var user = userUtils.getUserByUsername().getValue();

            var commentEnt = commentRepo.findById(commentId).orElseThrow();

            if (!user.getId().equals(commentEnt.getUserId()) && !user.getRole().equals(Role.ADMIN)) {
                throw new Exception("updateImageAd: Только автор объявления может вносить изменения");
            }

            return new ValueFromMethod(commentEnt);

        } catch (Exception ex) {
            return ValueFromMethod.resultErr(ex.getMessage());
        }
    }


    @Override
    public ValueFromMethod<Comments> getCommentsByAdvId(Integer id) {

        try {
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

        } catch (Exception ex) {
            return ValueFromMethod.resultErr(ex.getMessage());
        }
    }

    @Override
    public ValueFromMethod<Comment> addComment(Integer adId, CommentAdd comment) {
        try {
            var ad = advertisementRepository.findById(adId).orElseThrow();
            var user = userUtils.getUserByUsername().getValue();

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
            return ValueFromMethod.resultErr(ex.getMessage());
        }
    }

    @Override
    public ValueFromMethod<Comment> updateCommentForId(Integer id, CommentAdd commentAdd) {

        try {
            var resVerify = verifyUser(id);
            if (!resVerify.RESULT) {
                throw new Exception(resVerify.MESSAGE);
            }

            var commentEnt = resVerify.getValue();
            if (commentEnt.getText().equals(commentAdd.getText())) {
                throw new IllegalArgumentException("Повторный ввод комментария");
            }

            var user = userRepo.findById(commentEnt.getUserId()).orElseThrow();

            commentEnt.setText(commentAdd.getText());
            commentRepo.save(commentEnt);

            var commRes = initComment(commentEnt, user);

            return new ValueFromMethod(commRes);

        } catch (Exception ex) {
            return ValueFromMethod.resultErr(ex.getMessage());
        }

    }

    @Override
    public ValueFromMethod<Comment> deleteComment(Integer adId, Integer commentId) {
        try {
            var resVerify = verifyUser(commentId);
            if (!resVerify.RESULT) {
                throw new Exception(resVerify.MESSAGE);
            }

            commentRepo.deleteById(resVerify.getValue().getId());

            return ValueFromMethod.resultOk();

        } catch (Exception ex) {
            return ValueFromMethod.resultErr(ex.getMessage());
        }
    }
}
