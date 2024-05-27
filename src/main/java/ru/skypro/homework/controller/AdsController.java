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


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final UserUtils userUtils;
    private final AdvertisementService advertisementServ;
    private final CommentService commentService;

    // -----------------------------------

    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        ValueFromMethod<Comment> resData = commentService.deleteComment(adId, commentId);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/comments")
    public ResponseEntity addComment(@PathVariable Integer id, @RequestBody CommentAdd comment ) {

        ValueFromMethod<Comment> resData = commentService.addComment(id, comment);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<?> getCommentsForId(@PathVariable Integer id) {
       ValueFromMethod<Comments> resData = commentService.getCommentsByAdvId(id);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }

    @PatchMapping("{id}/comments")
    public ResponseEntity<?> updateCommentForId(@PathVariable Integer id, @RequestBody CommentAdd comment) {
        ValueFromMethod<Comment> resData = commentService.updateCommentForId(id, comment);

        if (!resData.RESULT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(resData.VALUE);
    }


    // -----------------------------------

    @GetMapping()
    public ResponseEntity<?> allAd() {

        var res = advertisementServ.allAd();
        if (!res.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(res.VALUE);
    }

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

    @GetMapping("me")
    public ResponseEntity<?> adForUser() {

        var res = advertisementServ.myAd();
        if (!res.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(res.VALUE);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAds(@RequestPart("properties") Adv adv,
                             @RequestPart("image") MultipartFile image) {

        if (!advertisementServ.addAd(adv, image)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImageAd(@PathVariable Integer id, @RequestParam MultipartFile image) {

        if (!advertisementServ.updateImageAd(id, image)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateAd(@PathVariable Integer id, @RequestBody Adv adv) {
        if (!advertisementServ.updateAd(id, adv)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
        if (!advertisementServ.deleteAd(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

}
