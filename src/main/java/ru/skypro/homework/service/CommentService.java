package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CommentAdd;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.utils.ValueFromMethod;

public interface CommentService {

    ValueFromMethod<Comments> getCommentsByAdvId(Integer id);

    ValueFromMethod<Comment> addComment(Integer adId, CommentAdd comment);

    ValueFromMethod<Comment> updateCommentForId(Integer id, CommentAdd commentAdd);

    ValueFromMethod<Comment> deleteComment(Integer adId, Integer commentId);
}
