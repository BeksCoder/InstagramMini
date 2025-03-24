package sultan.is.instagrammini.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.is.instagrammini.database.model.Image;

@Repository
public interface ImageRepository  extends JpaRepository<Image,Long> {
}
