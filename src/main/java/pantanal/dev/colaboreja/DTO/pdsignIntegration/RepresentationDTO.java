package pantanal.dev.colaboreja.DTO.pdsignIntegration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.AuthenticationTypeEnum;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.CompanyEnum;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepresentationDTO {

    @NotNull(message = "O campo willActAsPhysicalPerson é obrigatorio")
    private boolean willActAsPhysicalPerson;

    @NotNull(message = "O campo willActRepresentingAnyCompany é obrigatorio")
    private boolean willActRepresentingAnyCompany;

    @NotNull(message = "O campo companies é obrigatorio")
    private List<Map<String, String>> companies;

}