package ru.skypro.homework.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.repositories.UserAvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileAPI {

    private final UserAvatarRepository userAvatarRepository;
    private final Path PATH_FILE_AVATAR_DEFAULT;


    public FileAPI(@Value("${file.avatar.default}") String fileAvatarDefault,
                   @Value("${directory.img}") String dirImg,
                   UserAvatarRepository userAvatarRepository) {

        this.userAvatarRepository = userAvatarRepository;
        this.PATH_FILE_AVATAR_DEFAULT = Path.of(dirImg, fileAvatarDefault);
    }

/*    private static Path getRootPath(){
        return Path.of(System.getProperty("user.dir"));
    }*/

    private byte[] loadAvatar() throws IOException {

        return Files.readAllBytes(PATH_FILE_AVATAR_DEFAULT);
    }

    public byte[] loadAvatar(int idAvatar) throws IOException {
        var resultFind = userAvatarRepository.findById(idAvatar);

        if (resultFind.isEmpty()) {
            return loadAvatar();
        }

        return resultFind.orElseThrow().getData();
    }


}
