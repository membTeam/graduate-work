package ru.skypro.homework.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.enitities.CommentEnt;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEnt, Integer> {

    @Query(value = "from CommentEnt com where com.adId = :adId")
    List<CommentEnt> getListCommentByAdId(Integer adId);

    @Query(value = "select u.id author, com.id pk, u.first_name, com.text, com.created_at, COALESCE(ua.image, '/img/avatar/empty') image " +
            "from advertisement ad " +
            "join comment com on ad.id = com.ad_id and ad.id = :param " +
            "join users u on com.user_id = u.id " +
            "left join user_avatar ua on u.id = ua.id", nativeQuery = true)
    List<List<Object>> getCommentsByAdvertisement(Integer param);

}
