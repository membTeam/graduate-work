package ru.skypro.homework.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.UserRepository;

@Component
@Log4j
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepo;

    public ValueFromMethod<User> getUserByUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return getUserByUsername(userDetails);
    }

    public ValueFromMethod<User> getUserByUsername(UserDetails userDetails) {
        return getUserByUsername(userDetails.getUsername());
    }

    public ValueFromMethod<User> getUserByUsername(String username) {
        var user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("addAdv: пользователь не определен");
            return new ValueFromMethod(false, "addAdv: пользователь не определен");
        }

        return new ValueFromMethod<User>(user);
    }
}
