package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;

import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.enitities.User;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping("set-password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails user) {
        // TODO: использование repository
        var userResult = User.builder()
                .phone("+7")
                .firstName("firstName")
                .lastName("lastName")
                .build();
        return  ResponseEntity.ok(userResult);
    }

    @PatchMapping("me")
    public ResponseEntity<?> updateMe(@RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setImage(@RequestBody MultipartFile image) {
        return ResponseEntity.ok().build();
    }

}
