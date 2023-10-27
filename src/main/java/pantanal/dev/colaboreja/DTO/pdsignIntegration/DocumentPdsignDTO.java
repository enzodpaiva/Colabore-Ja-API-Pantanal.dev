package pantanal.dev.colaboreja.DTO.pdsignIntegration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentPdsignDTO {

    @NotNull(message = "O campo extension é obrigatorio")
    private String extension;

    @NotNull(message = "O campo isPendency é obrigatorio")
    private String isPendency;

    @NotNull(message = "O campo name é obrigatorio")
    private String name;

    @NotNull(message = "O campo order é obrigatorio")
    private String order;
    @NotNull(message = "O campo type é obrigatorio")
    private String type;

}
