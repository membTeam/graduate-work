package ru.skypro.homework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.WebResultPhoto;
import ru.skypro.homework.repositories.UserAvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileAPI {

    private static final Logger log = LoggerFactory.getLogger(FileAPI.class);
    private final UserAvatarRepository userAvatarRepository;
    private final Path PATH_FILE_AVATAR_DEFAULT;


    public FileAPI(@Value("${file.avatar.default}") String fileAvatarDefault,
                   @Value("${directory.img}") String dirImg,
                   UserAvatarRepository userAvatarRepository) {

        this.userAvatarRepository = userAvatarRepository;
        this.PATH_FILE_AVATAR_DEFAULT = Path.of(dirImg, fileAvatarDefault);
    }

    public byte[] loadDefaultAvatar() throws IOException {
        return Files.readAllBytes(PATH_FILE_AVATAR_DEFAULT);
    }

    public byte[] loadDefaultAvatar(int idAvatar) throws IOException {
        var resultFind = userAvatarRepository.findById(idAvatar);

        if (resultFind.isEmpty()) {
            return loadDefaultAvatar();
        }

        return resultFind.orElseThrow().getData();
    }

    public WebResultPhoto loadDefaultPhotoAvatar(){

        try {
            byte[] byteFromFile = Files.readAllBytes(PATH_FILE_AVATAR_DEFAULT);
            return WebResultPhoto.getWebResultPhoto(byteFromFile);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return WebResultPhoto.resultErr(ex.getMessage());
        }
    }

}
