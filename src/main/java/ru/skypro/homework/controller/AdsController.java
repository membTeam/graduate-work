package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;

/**
 * Контроллер объявлений
 */
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final UserUtils userUtils;
    private final AdvertisementService advertisementServ;
    private final CommentService commentService;

    // -----------------------------------

    /**
     * Конечная точка удаления комментария
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
     * Конечная точка добавления комментария
     * @param id
     * @param comment
     * @return
     */
    @PostMapping("{id}/comments")
    public ResponseEntity addComment(@PathVariable Integer id, @RequestBody CommentAdd comment ) {

        ValueFromMethod<Comment> resData = commentService.addComment(id, comment);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }


    /**
     * Конечная точка комментариев по заданному id объявления
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
     * Конечная точка изменения текста комментария
     * @param id
     * @param comment
     * @return
     */
    @PatchMapping("{id}/comments")
    public ResponseEntity<?> updateCommentForId(@PathVariable Integer id, @RequestBody CommentAdd comment) {
        ValueFromMethod<Comment> resData = commentService.updateCommentForId(id, comment);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }


    // -----------------------------------

    /**
     * Конечная точка получения список ВСЕХ объявлений
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
     * Конечная точка удаления объявления. Учитывается роль пользователя
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<?> detailsAd(@PathVariable Integer id) {
        UserDTO user = UserDTO.builder()
                .id(0)
                .phone("+7")
                .role("USER")
                .email("any@mail.ru")
                .image("imaleFile")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        return ResponseEntity.ok(user);
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
    public ResponseEntity<?> addAds(@RequestPart("properties") Adv adv,
                             @RequestPart("image") MultipartFile image) {

        if (!advertisementServ.addAd(adv, image)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<?> updateAd(@PathVariable Integer id, @RequestBody Adv adv) {
        if (!advertisementServ.updateAd(id, adv)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
