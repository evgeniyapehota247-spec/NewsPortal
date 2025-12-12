package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String email;
    private String password;
    private Integer userStatusId;
    private Integer roleId;
    private LocalDateTime createdAt;
    private String rememberToken;
    private LocalDateTime updatedAt;
    private UserDetails userDetails;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
