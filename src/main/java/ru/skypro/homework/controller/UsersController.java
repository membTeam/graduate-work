package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
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
import ru.skypro.homework.service.UserSerive;
import ru.skypro.homework.utils.ValueFromMethod;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserSerive userSerive;

    @PostMapping("set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword) {

        if (!userSerive.setPassword(newPassword).RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("me")
    public ResponseEntity<?> updateMe(@RequestBody UpdateUser updateUser) {

        var resultUpdate = userSerive.updateUser(updateUser);

        if (!resultUpdate.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(resultUpdate.VALUE);
    }

    @GetMapping("me")
    public ResponseEntity<?> me() {
        var resService = userSerive.getMyInfo();

        if (!resService.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(resService.VALUE);
    }

    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setImage(@RequestBody MultipartFile image) {
        if (!userSerive.setImage(image)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok().build();
    }

}
