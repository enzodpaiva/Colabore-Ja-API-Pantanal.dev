package pantanal.dev.colaboreja.DTO.pdsignIntegration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.CompanyEnum;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessPdsignDTO {

    @NotNull(message = "O campo title é obrigatorio")
    private String title;

    @NotNull(message = "O company é obrigatorio")
    private Map<String, String> company;

    @Valid
    @NotNull(message = "O member é obrigatorio")
    private List<MemberProcessDTO> members;

    @Valid
    @NotNull(message = "O flow é obrigatorio")
    private FlowProcessDTO flow;

    @NotNull(message = "A ação social é obrigatoria")
    private Long socialAction;

    @NotNull(message = "O colaborador é obrigatório")
    private Integer colaborator;
}
