package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.WebResultPhoto;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.UserAvatarRepository;
import ru.skypro.homework.service.ImgControllerService;
import ru.skypro.homework.utils.FileAPI;
import ru.skypro.homework.utils.UserUtils;


/**<pre>Реализация интерфейса ImgControllerService
 * предназначен для обработки фотографий и аватарок
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class ImgControllerServiceImpl implements ImgControllerService {
    private final AdvertisementRepository advertisementRepo;
    private final UserAvatarRepository userAvatarRepo;
    private final UserUtils userUtils;
    private final FileAPI fileAPI;


    /**
     * Получить фотографию объявления
     * @param image
     * @return
     */
    @Override
    public WebResultPhoto getPhotoAdv(String image) {
        try {
            var adv = advertisementRepo.findByImage("/img/adv/"+image).orElseThrow();

            HttpHeaders headers;
            headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(adv.getMetaData()));
            headers.setContentLength(adv.getSize());

            return WebResultPhoto.builder()
                    .result(true)
                    .message("ok")
                    .byteData(adv.getData())
                    .size(adv.getData().length)
                    .byteData(adv.getData())
                    .httpHeaders(headers)
                    .build();


        } catch (Exception ex) {
            return WebResultPhoto.resultErr ("getPhotoAdv: " + ex.getMessage());
        }
    }

    /**
     * Получить аватарку пользователя
     * @param image
     * @return
     */
    @Override
    public WebResultPhoto getImageAvatar(String image) {

        String metaData;
        WebResultPhoto result;

        try {
            if (image.equals("empty") || image.equals("/img/avatar/empty")) {
                result = fileAPI.loadDefaultPhotoAvatar();
                metaData = "image/png";
            } else {
                var userAvatar = userAvatarRepo.findByImage("/img/avatar/" + image);

                result = WebResultPhoto.getWebResultPhoto(userAvatar.getData());
                metaData = userAvatar.getMediaType();
            }

            HttpHeaders headers;
            headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(metaData));
            headers.setContentLength(result.getSize());

            result.setHttpHeaders(headers);

            return result;

        } catch (Exception ex) {
            return WebResultPhoto.resultErr ("getPhotoAdv: " + ex.getMessage());
        }
    }
}
