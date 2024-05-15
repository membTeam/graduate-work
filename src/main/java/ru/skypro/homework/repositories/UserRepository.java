package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skypro.homework.enitities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    @Query(value = "select exists(select * from users where username = :username)", nativeQuery = true)
    boolean userExists(String username);

}
