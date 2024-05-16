package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.service.AdvertisementService;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementServ;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> addAdv(@ModelAttribute Adv adv) {
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var res = advertisementServ.addAdv(adv);

        return ResponseEntity.ok().build();
    }

}
