package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserSerive {
    boolean setImage(MultipartFile image);
}
