package ru.skypro.homework.utils;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.skypro.homework.enitities.User;
import ru.skypro.homework.repositories.UserRepository;

@Component
@Log4j
public class UserUtils {

    private final UserRepository userRepo;

    private static User userAsDefault = null;

    public UserUtils(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private User getUserAsDefault() {
        if (userAsDefault == null) {
            setUserRepo();
        }
        return userAsDefault;
    }

    private void setUserRepo() {
        userAsDefault = userRepo.getDefaultUser();

        if (userRepo == null) {
            log.error("Нет пользователей с ролью USER");
            throw new UsernameNotFoundException("Нет пользователей с ролью USER");
        }
    }

    public ValueFromMethod<User> getUserByUsername() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return getUserByUsername(userDetails.getUsername());
        } catch (Exception ex) {
            return new ValueFromMethod(getUserAsDefault());
        }
    }

    public ValueFromMethod<User> getUserByUsername(UserDetails userDetails) {
        return getUserByUsername(userDetails.getUsername());
    }

    public ValueFromMethod<User> getUserByUsername(String username) {
        var user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("addAdv: пользователь не определен");
            return ValueFromMethod.resultErr("addAdv: пользователь не определен");
        }

        return new ValueFromMethod(user);
    }
}
