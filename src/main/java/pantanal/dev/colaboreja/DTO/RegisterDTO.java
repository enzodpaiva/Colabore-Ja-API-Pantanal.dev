package pantanal.dev.colaboreja.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.enumerable.RoleEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleEnum role;
}