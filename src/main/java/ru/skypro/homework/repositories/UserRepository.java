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

    @Query(value = "select case " +
            "when exists(select * from user_avatar av where av.id = :id ) " +
            "then (select image from user_avatar av where av.id = :id ) " +
            "else 'empty' end;", nativeQuery = true)
    String getImageAvatar(Integer id);

    @Query(value = "select us from Users us where us.id = (select min(u.id) from Users u where u.role = 'USER' );", nativeQuery = true)
    User getDefaultUser();

}
