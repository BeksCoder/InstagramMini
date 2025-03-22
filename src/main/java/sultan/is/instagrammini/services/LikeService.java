package sultan.is.instagrammini.services;

import java.util.List;

public interface LikeService {
    void addLike(Long userId, Long postId); // Добавить лайк

    void removeLike(Long userId, Long postId); // Удалить лайк

    long getLikesCount(Long postId); // Получить количество лайков для поста

    boolean isLikedByUser(Long userId, Long postId); // Проверить, поставил ли пользователь лайк на пост

    List<Long> getUserLikes(Long userId); // Получить все посты, которые лайкнул пользователь

    List<Long> getPostLikes(Long postId);
}
