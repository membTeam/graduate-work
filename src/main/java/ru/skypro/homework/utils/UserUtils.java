package ru.skypro.homework.utils;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepo;

    public ValueFromMethod<User> getUserByUsername() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            return getUserByUsername(userDetails.getUsername());
        } catch (Exception ex) {
            throw new UsernameNotFoundException("getUserByUsername: " + ex.getMessage() );
        }
    }

    public ValueFromMethod<User> getUserByUsername(UserDetails userDetails) {
        return getUserByUsername(userDetails.getUsername());
    }

    public ValueFromMethod<User> getUserByUsername(String username) {
        var user = userRepo.findByUsername(username);
        if (user == null) {
            return ValueFromMethod.resultErr("addAdv: пользователь не определен");
        }

        return new ValueFromMethod(user);
    }
}
