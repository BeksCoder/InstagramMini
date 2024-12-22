package sultan.is.instagrammini.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import sultan.is.instagrammini.database.common.BaseEntity;

import java.time.LocalDateTime;
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post extends BaseEntity {
    private String title;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;


}
