package sultan.is.instagrammini.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;
@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Image extends BaseEntity {
    private String imageUrl;
}
