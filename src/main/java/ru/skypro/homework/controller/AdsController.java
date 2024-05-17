package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.enitities.AdvertisementImg;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final UserUtils userUtils;
    private final AdvertisementService advertisementServ;
    private final AdvertisementRepository advertisementRepo;

    @GetMapping()
    public ResponseEntity<?> allAd() {
        List<Ad> lsAd = new ArrayList<>();

        Ads ads = Ads.builder()
                .count(0)
                .results(lsAd)
                .build();

        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{id}")
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

    @PatchMapping("/{id}/image")
    public ResponseEntity<?> updateImageAd(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAd(@PathVariable Integer id, @RequestBody Adv adv) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable Integer id) {
        return;
    }



}
