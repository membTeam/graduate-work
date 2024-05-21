package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.WebResultPhoto;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.UserAvatarRepository;
import ru.skypro.homework.service.ImgControllerService;

@Service
@RequiredArgsConstructor
public class ImgControllerServiceImpl implements ImgControllerService {
    private final AdvertisementRepository advertisementRepo;
    private final UserAvatarRepository userAvatarRepo;


    @Override
    public WebResultPhoto getPhotoAdv(String image) {
        try {
            var adv = advertisementRepo.findByImage("/"+image).orElseThrow();

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
}
