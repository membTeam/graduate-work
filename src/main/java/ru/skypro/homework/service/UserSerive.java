package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.utils.ValueFromMethod;

public interface UserSerive {
    ValueFromMethod<UserDTO> getMyInfo();

    boolean setImage(MultipartFile image);

    ValueFromMethod setPassword(NewPassword newPassword);

    ValueFromMethod updateUser(UpdateUser updateUser);
}
