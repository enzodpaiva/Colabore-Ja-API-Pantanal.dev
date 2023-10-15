package pantanal.dev.colaboreja.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialActionCategoryDTO {

    @Null
    private Long id;

    @NotBlank(message = "O nome deve possuir de 1 a 255 caracteres")
    private String name;
}
