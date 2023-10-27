package pantanal.dev.colaboreja.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialActionContractDTO {

    @Null
    private Long id;

    @Nullable
    private String keyProcess;

    @Nullable
    private String keyDocument;

    @NotNull(message = "O status do contrato da ação social deve ser informado")
    private SocialActionContractStatusEnum statusContract;

    @NotNull(message = "A ação social é obrigatoria")
    private Long socialActionId;

    @NotNull(message = "O colaborador é obrigatório")
    private Integer colaborator;

    @Nullable
    private String codeDocumentPdsign;

}
