package sultan.is.instagrammini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.is.instagrammini.database.model.Post;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post,Long> {

    List<Post> findAllByUserId(Long userId);
}
