package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skypro.homework.enitities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u From User u where u.email = :username")
    User findByUsername(String username);

    @Query(value = "select exists(select * from users where email = :username)", nativeQuery = true)
    boolean userExists(String username);

    @Query(value = "select case" +
            " when exists( select * from users where email = :username) = true" +
            " then (select id from users where email = :username)" +
            " else -1 end;", nativeQuery = true)
    Integer getUserIdByUsername(String username);

}
