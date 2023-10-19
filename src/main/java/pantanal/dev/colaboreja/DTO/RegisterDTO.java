package pantanal.dev.colaboreja.DTO;

import jakarta.validation.constraints.Email;
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
    @Email(message = "Email invalido")
    private String email;
    private String password;
    private RoleEnum role;
}