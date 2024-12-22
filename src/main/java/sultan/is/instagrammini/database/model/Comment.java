package sultan.is.instagrammini.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sultan.is.instagrammini.database.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    private String comment;
    private LocalDateTime createdAt;
}
