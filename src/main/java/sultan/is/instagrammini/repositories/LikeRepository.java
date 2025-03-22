package sultan.is.instagrammini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.is.instagrammini.database.model.Like;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);  // Проверка на существование лайка

    long countByPostId(Long postId);  // Получить количество лайков для поста

    List<Like> findByPostId(Long postId);  // Получить все лайки для поста

    List<Like> findByUserId(Long userId);
}
