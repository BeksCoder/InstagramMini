package sultan.is.instagrammini.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;
import sultan.is.instagrammini.database.enums.Gender;
@Entity
@Table(name = "userInfo")
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Builder
public class UserInfo extends BaseEntity {
    private String fullName;
    private String biography;
    private Gender gender;
    private String image;
}
