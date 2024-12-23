package sultan.is.instagrammini.database.model;

import jakarta.persistence.*;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;
import sultan.is.instagrammini.database.enums.Gender;
@Entity
@Table(name = "userInfo")
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Builder
@SequenceGenerator(name = "base_id_gen", sequenceName = "user_info_seq", allocationSize = 1)

public class UserInfo extends BaseEntity {
    private String fullName;
    private String biography;
    private Gender gender;
    private String image;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
