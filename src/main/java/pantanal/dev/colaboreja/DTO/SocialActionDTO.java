package pantanal.dev.colaboreja.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialActionDTO {

    @Null
    private Long id;

    @NotNull(message = "O nome deve possuir de 1 a 255 caracteres")
    private String name;

    @NotNull(message = "A descrição deve possuir de 1 a 255 caracteres")
    private String description;

    @NotNull(message = "A data e hora de incio é obrigatória.")
    private Date initDateTime;

    @NotNull(message = "A data e hora de fim é obrigatória.")
    private Date finishDateTime;

    @NotNull(message = "A categoria da ação social é obrigatoria")
    private Long socialActionCategoryId;

    @NotNull(message = "O autor é obrigatório")
    private Integer author;

    @NotBlank(message = "A localização é obrigatória")
    private String location;


}
