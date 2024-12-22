package sultan.is.instagrammini.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;
@Entity
@Table(name = "likes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Like extends BaseEntity {
    private boolean IsLike;
}
