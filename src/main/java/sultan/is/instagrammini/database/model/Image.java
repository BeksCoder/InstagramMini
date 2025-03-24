package sultan.is.instagrammini.database.model;

import jakarta.persistence.*;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;
@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@SequenceGenerator(name = "base_id_gen", sequenceName = "image_seq", allocationSize = 1)

public class Image extends BaseEntity {
    private String imageUrl;
    private String description;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
