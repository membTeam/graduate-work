package ru.skypro.homework.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.Adv;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.enitities.Advertisement;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.enitities.UserAvatar;
import ru.skypro.homework.repositories.AdvertisementRepository;
import ru.skypro.homework.repositories.UserAvatarRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdvertisementService;
import ru.skypro.homework.utils.FileAPI;


@SpringBootTest
public class AdvertisementServiceTest {

    @Autowired
    private FileAPI fileAPI;

    @Autowired
    private AdvertisementService advertisementServ;

    @MockBean
    private AdvertisementRepository advertisementRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private UserAvatarRepository userAvatarRepo;

    @Test
    public void deleteAd() {

        var user = User.builder()
                .id(1)
                .build();

        var adv = Advertisement.builder()
                .id(1)
                .userId(user.getId())
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(advertisementRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(adv));
        doNothing().when(advertisementRepo).deleteById(any(Integer.class));

        var result = advertisementServ.deleteAd(1);

        assertTrue(result);
    }

    @Test
    public void updateAd() {
        var user = User.builder().id(1).build();

        var advEnt = Advertisement.builder()
                .id(1).userId(user.getId())
                .build();

        var adv = Adv.builder()
                .price(3000)
                .title("Позиционирование товара")
                .description("Описание выставленного товара")
                .build();

        var advSaveEnt = Advertisement.builder()
                .id(1)
                .userId(user.getId())
                .title(adv.getTitle())
                .price(adv.getPrice())
                .description(adv.getDescription())
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(advertisementRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(advEnt));
        when(advertisementRepo.save(any(Advertisement.class))).thenReturn(advSaveEnt);

        var result = advertisementServ.updateAd(1, adv);

        assertTrue(result);
    }

    @Test
    public void updateImageAd() throws IOException {
        var strFile = "imageForTest.png";
        var user = User.builder()
                .id(1)
                .role(Role.USER)
                .build();

        var advEnt = Advertisement.builder()
                .id(1)
                .userId(user.getId())
                .build();

        var pathPhoto = fileAPI.getPathDirImage().resolve(strFile);

        var hashId = user.getId().toString().hashCode();
        var strImage = String.format("/img/avatar/avatar-%d", hashId);

        var userAvatarAfterSave = UserAvatar.builder()
                .id(1)
                .image(strImage)
                .build();

        byte[] data = Files.readAllBytes(pathPhoto);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                String.valueOf(pathPhoto.getFileName()),
                String.valueOf(MediaType.IMAGE_PNG),
                data
        );

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userAvatarRepo.save(any(UserAvatar.class))).thenReturn(userAvatarAfterSave);
        when(advertisementRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(advEnt));

        var result = advertisementServ.updateImageAd(1, file);

        assertTrue(result);
    }

    @Test
    public void addAd() throws IOException {
        var strFile = "imageForTest.png";
        var user = User.builder()
                .id(1)
                .role(Role.USER)
                .build();

        var adv = Adv.builder()
                .price(3000)
                .title("Позиционирование товара")
                .description("Описание выставленного товара")
                .build();

        var advEnt = Advertisement.builder()
                .id(1)
                .userId(user.getId())
                .build();

        var pathPhoto = fileAPI.getPathDirImage().resolve(strFile);
        var hashId = user.getId().toString().hashCode();
        var strImage = String.format("/img/avatar/avatar-%d", hashId);

        var advAfterSave = Advertisement.builder()
                .id(1)
                .image(strImage)
                .title(adv.getTitle())
                .price(adv.getPrice())
                .description(adv.getDescription())
                .build();

        byte[] data = Files.readAllBytes(pathPhoto);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                String.valueOf(pathPhoto.getFileName()),
                String.valueOf(MediaType.IMAGE_PNG),
                data
        );

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(advertisementRepo.save(any(Advertisement.class))).thenReturn(advAfterSave);
        when(advertisementRepo.findById(any(Integer.class))).thenReturn(Optional.ofNullable(advEnt));

        var result = advertisementServ.addAd(adv, file);

        assertTrue(result);

    }


    @Test
    public void allAd() {

        List<Advertisement> lsAdv = List.of(
                Advertisement.builder()
                        .id(1).userId(1).title("title").price(1000).image("image")
                        .build());

        when(advertisementRepo.findAll()).thenReturn(lsAdv);

        var result = advertisementServ.allAd();
        var data = (Ads) result.getValue();

        assertTrue(result.RESULT);

        assertEquals(1, data.getCount());
        assertEquals(1, data.getResults().size());

    }

}
