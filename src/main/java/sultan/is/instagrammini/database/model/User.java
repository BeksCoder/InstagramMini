package sultan.is.instagrammini.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import sultan.is.instagrammini.database.common.BaseEntity;
@Entity
@Table(name = "users")
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;

}
