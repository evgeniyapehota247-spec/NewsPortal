package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

    private Integer userId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
}
