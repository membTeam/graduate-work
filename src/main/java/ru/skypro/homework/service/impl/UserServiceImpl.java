package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.enitities.UserAvatar;
import ru.skypro.homework.repositories.UserAvatarRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserSerive;
import ru.skypro.homework.utils.UserUtils;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.utils.ValueFromMethod;

import javax.transaction.Transactional;

@Service
@Log4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserSerive {

    private final UserRepository userRepo;
    private final UserUtils userUtils;
    private final UserAvatarRepository userAvatarRepo;
    private final PasswordEncoder encoder;


    @Override
    public ValueFromMethod<UserDTO> getMyInfo() {
        var resFromUtils = userUtils.getUserByUsername();

        if (!resFromUtils.RESULT) {
            var str = "Пользователь не найден";
            log.error(str);
            return new ValueFromMethod(false, str);
        }

        var user = resFromUtils.getValue();
        var userAvatarFromRepo = userAvatarRepo.findById(user.getId());

        String image = "/img/adv/image/empty";
        if (userAvatarFromRepo.isPresent()) {
            image = (userAvatarFromRepo.orElseThrow()).getImage();
        }

        var userDTO = UserDTO.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .image(image)
                .build();

        return new ValueFromMethod(userDTO);
    }

    @Override
    @Transactional
    public boolean setImage(MultipartFile image) {

        try {
            var dataFromUserUtils = userUtils.getUserByUsername();

            if (!dataFromUserUtils.RESULT) {
                throw  new Exception(dataFromUserUtils.MESSAGE);
            }

            User user = dataFromUserUtils.getValue();

            var hashId = user.getId().toString().hashCode();
            var strImage = String.format("/img/avatar/avatar-%d", hashId);

            if (userAvatarRepo.existsImgAvatar(strImage)) {
                throw new IllegalArgumentException("Повторный ввод image аватара");
            }

            UserAvatar userAvatar = UserAvatar.builder()
                    .user(user)
                    .fileSize(image.getSize())
                    .mediaType(image.getContentType())
                    .image(strImage)
                    .data(image.getBytes())
                    .build();

            if (userAvatarRepo.existsUserAvatar(user.getId())) {
                userAvatarRepo.deleteById(user.getId());
            }

            userAvatarRepo.save(userAvatar);
            return true;

        } catch (Exception ex) {
            ValueFromMethod.resultErr("setImage: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public ValueFromMethod<User> setPassword(NewPassword newPassword) {

        try {
            var user = userUtils.getUserByUsername().getValue();

            if (newPassword.getNewPassword().isBlank()) {
                throw new IllegalArgumentException("Пустой пароль");
            }

            if (!encoder.matches(newPassword.getCurrentPassword(), user.getPassword() )) {
                throw new Exception("Ошибка текущего пароля");
            }

            var newPsw = encoder.encode(newPassword.getNewPassword());
            user.setPassword(newPsw);

            var userSave = userRepo.save(user);

            return new ValueFromMethod(userSave);

        } catch (Exception ex) {
            return ValueFromMethod.resultErr("setPassword: " + ex.getMessage());
        }
    }

    @Override
    public ValueFromMethod<UpdateUser> updateUser(UpdateUser updateUser) {

        try {
            var user = userUtils.getUserByUsername().VALUE;

            user.setPhone(updateUser.getPhone());
            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());

            var userSave = userRepo.save(user);

            var result = UpdateUser.builder()
                    .phone(userSave.getPhone())
                    .firstName(userSave.getFirstName())
                    .lastName(userSave.getLastName())
                    .build();

            return new ValueFromMethod(result);

        } catch (Exception ex) {
            return ValueFromMethod.resultErr("updateUser: " + ex.getMessage());
        }
    }

}
