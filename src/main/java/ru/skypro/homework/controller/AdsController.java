package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;

import javax.validation.Valid;
import org.springframework.validation.Errors;


/**
 * Контроллер предназначен для: <pre>
 *     удаление/добавление/обновление комментариев
 *     удаление/добавление/обновление объявлений
 *     изменений фотографий объявлений
 *     список всех объявлений
 *     Общий список объявлений допускается просматривать не зарегистрированному пользователю
 *     ВСЕ остальные методы только для зарегистрированных пользователей
 * </pre>
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private static final Logger log = LoggerFactory.getLogger(AdsController.class);
    private final UserUtils userUtils;
    private final AdvertisementService advertisementServ;
    private final CommentService commentService;
    private final UserRepository userRepository;

    // -----------------------------------

    /**
     * удаление комментария
     * @param adId
     * @param commentId
     * @return
     */
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        ValueFromMethod<Comment> resData = commentService.deleteComment(adId, commentId);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }


    /**
     * добавление комментария
     * @param id
     * @param comment
     * @return
     */
    @PostMapping("{id}/comments")
    public ResponseEntity addComment(@PathVariable Integer id, @Valid @RequestBody CommentAdd comment, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ValueFromMethod<Comment> resData = commentService.addComment(id, comment);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }


    /**
     * комментарии по заданному id объявления
     * @param id
     * @return
     */
    @GetMapping("{id}/comments")
    public ResponseEntity<?> getCommentsForId(@PathVariable Integer id) {
       ValueFromMethod<Comments> resData = commentService.getCommentsByAdvId(id);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }

    /**
     * изменение текста комментария
     * @param adId
     * @param comment
     * @return
     */
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> updateCommentForId(@PathVariable Integer adId,
                                                @PathVariable Integer commentId,
                                                @Valid @RequestBody CommentAdd comment, Errors errors) {

        if (errors.hasErrors()) {
            log.error("updateCommentForId: " + errors.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ValueFromMethod<Comment> resData = commentService.updateCommentForId(adId, commentId, comment);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }


    // -----------------------------------

    /**
     * Список ВСЕХ объявлений
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> allAd() {

        ValueFromMethod<Ads> res = advertisementServ.allAd();
        if (!res.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(res.getValue());
    }

    /**
     * Удаление объявления
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<?> detailsAd(@PathVariable Integer id) {
        var res = advertisementServ.detailsAd(id);
        if (!res.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(res.getValue());
    }

    /**
     * Конечная точка объявлений, созданных пользователем
     * @return
     */
    @GetMapping("me")
    public ResponseEntity<?> adForUser() {

        var res = advertisementServ.myAd();
        if (!res.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(res.VALUE);
    }

    /**
     * Конечная точка добавления объявления
     * @param adv
     * @param image
     * @return
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAds(@Valid @RequestPart("properties") CreateOrUpdateAd adv, Errors errors,
                                    @RequestPart("image") MultipartFile image) {

        if (errors.hasErrors()) {
            log.error("addAds: " + errors.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (!advertisementServ.addAd(adv, image)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Конечная точка изменения фотографии объявления
     * @param id
     * @param image
     * @return
     */
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImageAd(@PathVariable Integer id, @RequestParam MultipartFile image) {

        if (!advertisementServ.updateImageAd(id, image)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Конечная точка изменения объявления
     * @param id
     * @param adv
     * @return
     */
    @PatchMapping("{id}")
    public ResponseEntity<?> updateAd(@PathVariable Integer id, @Valid @RequestBody CreateOrUpdateAd adv, Errors errors ) {

        if (errors.hasErrors()) {
            log.error("updateAd: " + errors.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (!advertisementServ.updateAd(id, adv)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Конечная точка удаления объявления. Учитывается роль пользователяы
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (!advertisementServ.deleteAd(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

}
