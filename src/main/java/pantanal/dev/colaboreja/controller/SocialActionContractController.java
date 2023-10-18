package pantanal.dev.colaboreja.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pantanal.dev.colaboreja.DTO.SocialActionContractDTO;
import pantanal.dev.colaboreja.DTO.SocialActionDTO;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import pantanal.dev.colaboreja.service.SocialActionContractService;
import pantanal.dev.colaboreja.util.ApiResponse;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/social-action-contract")
public class SocialActionContractController {
    @Autowired
    private SocialActionContractService service;

    private SocialActionContractDTO convertToDTO(SocialActionContractModel socialActionContractModel) {
        return SocialActionContractDTO.builder()
                .id(socialActionContractModel.getId())
                .keyContract(socialActionContractModel.getKeyContract())
                .statusContract(Objects.toString(socialActionContractModel.getStatusContract(), null))
                .socialActionId(socialActionContractModel.getSocialActionId().getId())
                .build();
    }

    @GetMapping
    public Collection<SocialActionContractDTO> getAllSocialActionContract(
            @Parameter(description = "Nome da ação social")
            @RequestParam(value = "name", required = false) String keyContract) {
        try {
            return this.service.findSocialActionContract(keyContract).stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getSocialActionContract(
            @Parameter(description = "identificador") @PathVariable(value = "id", required = true) Long id) {

        try {
            var socialActionContract = this.service.getSocialActionContractById(id);

            return ResponseEntity.ok(convertToDTO(socialActionContract.get()));
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<SocialActionContractDTO> createSocialAction(@Valid @RequestBody SocialActionContractDTO socialAction) {
        try {
            var result = this.service.createSocialActionContract(socialAction);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(result));
        } catch(Exception ex ){
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSocialActionContract(
            @PathVariable Long id,
            @Valid @RequestBody SocialActionContractDTO socialAction) {
        try {
            var existingSocialAction = this.service.updateSocialActionContract(id, socialAction);
            ApiResponse response = new ApiResponse(true, "Contrato de ação social atualizado com sucesso", convertToDTO(existingSocialAction));

            return ResponseEntity.ok(response);
        }catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSocialActionContract(@PathVariable(value = "id", required = true) Long id) {
        try {
            var socialActionContract = this.service.getSocialActionContractById(id);
            this.service.deleteSocialActionContract(socialActionContract.get().getId());

            ApiResponse response = new ApiResponse(true, "Contrato de ação social deletado com sucesso", convertToDTO(socialActionContract.get()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
