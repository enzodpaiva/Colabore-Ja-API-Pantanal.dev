package pantanal.dev.colaboreja.DTO.pdsignIntegration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.enumerable.pdsignIntegration.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberProcessDTO {

    @NotNull(message = "O campo name é obrigatorio")
    private String name;

    @Email
    @NotNull(message = "O campo email é obrigatorio")
    private String email;

    @NotNull(message = "O campo documentType é obrigatorio")
    private String documentType;

    @NotNull(message = "O campo documentCode é obrigatorio")
    private String documentCode;

    @NotNull(message = "O campo actionType é obrigatorio")
    private Map<String, String>  actionType;

    @NotNull(message = "O campo responsibility é obrigatorio")
    private Map<String, String> responsibility;

    @NotNull(message = "O campo authenticationType é obrigatorio")
    private Map<String, String> authenticationType;

    @NotNull(message = "O campo order é obrigatorio")
    private String order;

    @NotNull(message = "O campo type é obrigatorio")
    private String type;

    @Valid
    @NotNull(message = "O representation é obrigatorio")
    private RepresentationDTO representation;

}