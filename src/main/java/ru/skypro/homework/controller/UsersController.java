package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.service.UserSerive;

import javax.validation.Valid;


/**<pre>Контроллер обработки данных пользователя:
 * изменение пароля
 * детализация пользователя
 * изменение данных пользователя
 * изменения аватарки
 * </pre> *
 */
@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserSerive userSerive;

    /**
     * endPoint изменения пароля
     * @param newPassword
     * @return
     */
    @PostMapping("set_password")
    public ResponseEntity<?> setPassword(@Valid @RequestBody NewPassword newPassword, Errors error) {

        if (error.hasErrors()) {
            log.error("setPassword " + error.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (!userSerive.setPassword(newPassword).RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok().build();
    }

    /**
     * endPoint изменения данных пользователя
     * @param updateUser
     * @return
     */
    @PatchMapping("me")
    public ResponseEntity<?> updateMe(@Valid @RequestBody UpdateUser updateUser, Errors error) {

        if (error.hasErrors()) {
            log.error("updateMe: " + error.getAllErrors().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var resultUpdate = userSerive.updateUser(updateUser);

        if (!resultUpdate.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(resultUpdate.VALUE);
    }

    /**
     * <pre>endPoint выборка структуры данных пользователя:
     * имя
     * фамилия
     * телефон
     * </pre>
     * @return
     */
    @GetMapping("me")
    public ResponseEntity<?> me() {
        var resService = userSerive.getMyInfo();

        if (!resService.RESULT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(resService.VALUE);
    }

    /**
     *Изменение аватарки
     * @param image
     * @return
     */
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setImage(@RequestBody MultipartFile image) {
        if (!userSerive.setImage(image)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok().build();
    }

}
