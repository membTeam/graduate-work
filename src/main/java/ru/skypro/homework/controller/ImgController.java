package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.WebResultPhoto;
import ru.skypro.homework.service.ImgControllerService;


/** <pre>контроллер снабжает
 * пользователей аватарками
 * объявления фотографиями
 * </pre>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/img")
public class ImgController {

    private final ImgControllerService imgControllerService;

    /**
     * image аватарок
     * @param image
     * @return
     */
    @GetMapping("avatar/{image}")
    public ResponseEntity<byte[]> getImagAvatar(@PathVariable String image) {
        WebResultPhoto result = imgControllerService.getImageAvatar(image);

        if (!result.isResult()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok()
                .headers(result.getHttpHeaders())
                .body(result.getByteData());
    }


    /**
     * фотографии объявлений
     * @param image
     * @return
     */
    @GetMapping("adv/{image}")
    public ResponseEntity<byte[]> getImagAdv(@PathVariable String image) {
        WebResultPhoto result = imgControllerService.getPhotoAdv(image);

        if (!result.isResult()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok()
                .headers(result.getHttpHeaders())
                .body(result.getByteData());
    }
}
