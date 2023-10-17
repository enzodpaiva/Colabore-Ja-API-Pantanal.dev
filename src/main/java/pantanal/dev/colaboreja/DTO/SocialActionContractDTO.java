package pantanal.dev.colaboreja.DTO;

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
public class SocialActionContractDTO {

    @Null
    private Long id;

    private String keyContract;

    @NotNull(message = "O status do contrato da ação social deve ser informado")
    private String statusContract;

    @NotNull(message = "A ação social é obrigatoria")
    private Long socialActionId;

}
