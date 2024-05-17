package ru.skypro.homework.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.repositories.UserAvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileAPI {

    private final UserAvatarRepository userAvatarRepository;
    private final String fileAvatarDefault;

    public FileAPI(@Value("${file.avatar.default}") String fileAvatarDefault, UserAvatarRepository userAvatarRepository) {
        this.userAvatarRepository = userAvatarRepository;
        this.fileAvatarDefault = fileAvatarDefault;
    }


    private static Path getRootPath(){
        return Path.of(System.getProperty("user.dir"));
    }

    private byte[] loadAvatar() throws IOException {
        var path = getRootPath().resolve(Paths.get(fileAvatarDefault));

        return Files.readAllBytes(path);
    }

    public byte[] loadAvatar(int idAvatar) throws IOException {
        var resultFind = userAvatarRepository.findById(idAvatar);

        if (resultFind.isEmpty()) {
            return loadAvatar();
        }

        return resultFind.orElseThrow().getData();
    }


}
