package ru.skypro.homework.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.enitities.UserAvatar;
import ru.skypro.homework.repositories.UserAvatarRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;
import ru.skypro.homework.utils.FileAPI;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.utils.ValueFromMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private FileAPI fileAPI;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserAvatarRepository userAvatarRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private UserUtils userUtils;


    @Test
    public void setImage_existsImageAvatar() throws IOException {
        var strFile = "avatar.png";

        var pathPhoto = fileAPI.getPathDirImage().resolve(strFile);
        var user = User.builder()
                .id(1)
                .role(Role.USER)
                .build();

        byte[] data = Files.readAllBytes(pathPhoto);
        MockMultipartFile image = new MockMultipartFile(
                "file",
                String.valueOf(pathPhoto.getFileName()),
                String.valueOf(MediaType.IMAGE_PNG),
                data
        );

        var originName = image.getOriginalFilename();
        var file = originName.substring(0, originName.lastIndexOf('.'));

        var hashId = (user.getId().toString() + file).hashCode();
        var strFormat = hashId > 0
                ? "/img/avatar/avatar-%d"
                : "/img/avatar/img-avatar%d";

        var strImage = String.format(strFormat, hashId);

        var userAvatarAfterSave = UserAvatar.builder()
                .id(1)
                .image(strImage)
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userAvatarRepo.save(any(UserAvatar.class))).thenReturn(userAvatarAfterSave);
        when(userAvatarRepo.existsImgAvatar(any(String.class))).thenReturn(true);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));

        assertTrue(userServiceImpl.setImage(image));
    }


    @Test
    public void setImage_notExistsImageAvatar() throws IOException {
        var strFile = "avatar-default.png";

        var pathPhoto = fileAPI.getPathDirImage().resolve(strFile);
        var user = User.builder()
                .id(1)
                .role(Role.USER)
                .build();

        byte[] data = Files.readAllBytes(pathPhoto);
        MockMultipartFile image = new MockMultipartFile(
                "file",
                String.valueOf(pathPhoto.getFileName()),
                String.valueOf(MediaType.IMAGE_PNG),
                data
        );

        var originName = image.getOriginalFilename();
        var file = originName.substring(0, originName.lastIndexOf('.'));

        var hashId = (user.getId().toString() + file).hashCode();
        var strFormat = hashId > 0
                ? "/img/avatar/avatar-%d"
                : "/img/avatar/img-avatar%d";

        var strImage = String.format(strFormat, hashId);

        var userAvatarAfterSave = UserAvatar.builder()
                .id(null)
                .image(strImage)
                .build();

        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));

        when(userAvatarRepo.existsUserAvatar(any(Integer.class))).thenReturn(false);
        when(userAvatarRepo.existsImgAvatar(any(String.class))).thenReturn(false);
        when(userAvatarRepo.save(any(UserAvatar.class))).thenReturn(userAvatarAfterSave);

        assertTrue(userServiceImpl.setImage(image));
    }

    @Test
    public void getMyInfo() {

        var user = User.builder()
                .role(Role.USER)
                .phone("+7")
                .email("usermail@mail.ru")
                .role(Role.USER)
                .firstName("firtName")
                .lastName("lastName")
                .id(1)
                .build();

        var userDTO = UserDTO.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .image(user.getAvatar() != null
                        ? user.getAvatar().getImage()
                        : "/img/avatar/empty" )
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userAvatarRepo.findById(user.getId())).thenReturn(Optional.empty());
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));

        var res = userServiceImpl.getMyInfo();

        assertTrue(res.RESULT);
        assertEquals("/img/avatar/empty", res.getValue().getImage());
    }

    @Test
    public void setPassword() {
        var newPsw = "newPass";
        var user = User.builder()
                .id(1)
                .role(Role.USER)
                .password(encoder.encode("password"))
                .build();

        var userSave = User.builder()
                .id(1)
                .password(encoder.encode(newPsw))
                .build();

        var newPassword = NewPassword.builder()
                .newPassword(newPsw)
                .currentPassword("password")
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(userSave);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));

        var result = userServiceImpl.setPassword(newPassword);
        assertTrue(result.RESULT);
    }

    @Test
    public void updateUser() {
        var user = User.builder()
                .id(1)
                .build();

        UpdateUser updUser = UpdateUser.builder()
                .phone("+79185736336")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var userSave = User.builder()
                .id(1)
                .phone(updUser.getPhone())
                .firstName(updUser.getFirstName())
                .lastName(updUser.getLastName())
                .build();

        when(userRepo.getDefaultUser()).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(userSave);
        when(userUtils.getUserByUsername()).thenReturn(new ValueFromMethod(user));

        var result = userServiceImpl.updateUser(updUser);

        assertTrue(result.RESULT);

        var value = result.getValue();

        assertEquals(updUser.getFirstName(), value.getFirstName());
        assertEquals(updUser.getLastName(), value.getLastName());
        assertEquals(updUser.getPhone(), value.getPhone());

    }

}
