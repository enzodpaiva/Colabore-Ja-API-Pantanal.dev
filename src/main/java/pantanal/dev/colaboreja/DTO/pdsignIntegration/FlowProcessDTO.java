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
public class FlowProcessDTO {

    @NotNull(message = "O defineOrderOfInvolves é obrigatorio")
    private boolean defineOrderOfInvolves;

    @NotNull(message = "O hasExpiration é obrigatorio")
    private boolean hasExpiration;

    @NotNull(message = "O readRequired é obrigatorio")
    private boolean readRequired;

    @NotNull(message = "O convertDocumentToPdfA é obrigatorio")
    private boolean convertDocumentToPdfA;

    @NotNull(message = "O signWithCades é obrigatorio")
    private boolean signWithCades;

}