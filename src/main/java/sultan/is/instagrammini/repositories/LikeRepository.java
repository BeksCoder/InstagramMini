package sultan.is.instagrammini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sultan.is.instagrammini.database.model.Like;
import sultan.is.instagrammini.database.model.Post;
import sultan.is.instagrammini.database.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    @Query("select count(l) from Like l where l.post.id = :postId")
    long countByPostId(@Param("postId") Long postId);

    List<Like> findByPostId(Long postId);

    List<Like> findByUserId(Long userId);

    Optional<Like> findByUserAndPost(User user, Post post);

}
