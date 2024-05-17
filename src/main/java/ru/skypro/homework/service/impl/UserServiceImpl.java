package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public ValueFromMethod<UserDTO> getMyInfo() {
        var resFromUtils = userUtils.getUserByUsername();

        if (!resFromUtils.RESULT) {
            var str = "Пользователь не найден";
            log.error(str);
            return new ValueFromMethod(false, str);
        }

        var user = resFromUtils.VALUE;
        var userAvatarFromRepo = userAvatarRepo.findById(user.getId());

        String image = "not data";
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var dataFromUserUtils = userUtils.getUserByUsername(userDetails);

        if (!dataFromUserUtils.RESULT) {
            log.error(dataFromUserUtils.MESSAGE);
            return false;
        }

        try {
            User user = dataFromUserUtils.VALUE;

            var hashId = user.getId().toString().hashCode();
            var strImage = String.format("ava-%d", hashId);

            UserAvatar userAvatar = UserAvatar.builder()
                    .user(user)
                    .fileSize(image.getSize())
                    .mediaType(image.getContentType())
                    .image(strImage)
                    .data(image.getBytes())
                    .build();

            if ((userAvatarRepo.findById(user.getId()).isPresent() )) {
                userAvatarRepo.deleteById(user.getId());
            }

            userAvatarRepo.save(userAvatar);
            return true;

        } catch (Exception ex) {
            log.error("setImage: " + ex.getMessage());
            return false;
        }

    }
}
