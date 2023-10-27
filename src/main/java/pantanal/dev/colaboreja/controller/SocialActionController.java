package pantanal.dev.colaboreja.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pantanal.dev.colaboreja.DTO.SocialActionDTO;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.service.SocialActionService;
import pantanal.dev.colaboreja.util.ApiResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/social-action")
public class SocialActionController {

    @Autowired
    private SocialActionService service;

    private SocialActionDTO convertToDTO(SocialActionModel socialAction) {
        return SocialActionDTO.builder()
                .id(socialAction.getId())
                .name(socialAction.getName())
                .description(socialAction.getDescription())
                .initDateTime(socialAction.getInitDateTime())
                .finishDateTime(socialAction.getFinishDateTime())
                .socialActionCategoryId(socialAction.getSocialActionCategoryId().getId())
                .author(socialAction.getAuthor().getId())
                .location(socialAction.getLocation())
                .build();
    }

    @GetMapping
    public Collection<SocialActionDTO> getAllSocialAction(
            @Parameter(description = "Nome da ação social")
            @RequestParam(value = "name", required = false) String texto) {
        try {
            return this.service.findSocialAction(texto).stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw ex;
        }
    }

    //    -- origens de request permitidas
//    @CrossOrigin(origins = {"http://localhost:8080", "https://www.google.com"} )
//    -- cors personalizado por endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getSocialAction(
            @Parameter(description = "identificador") @PathVariable(value = "id", required = true) Long id) {

        try {
            var socialAction = this.service.getSocialActionById(id);

            return ResponseEntity.ok(convertToDTO(socialAction.get()));
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<SocialActionDTO> createSocialAction(@Valid @RequestBody SocialActionDTO socialAction) {
        try {
            var result = this.service.createSocialAction(socialAction);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(result));
        } catch(Exception ex ){
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSocialAction(
            @PathVariable Long id,
            @Valid @RequestBody SocialActionDTO socialAction) {
        try {
            var existingSocialAction = this.service.updateSocialAction(id, socialAction);

            ApiResponse response = new ApiResponse(true, "Ação social atualizada com sucesso", convertToDTO(existingSocialAction));
            return ResponseEntity.ok(response);
        }catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSocialAction(@PathVariable(value = "id", required = true) Long id) {
        try {
            var socialAction = this.service.getSocialActionById(id);
            this.service.deleteSocialAction(socialAction.get().getId());

            ApiResponse response = new ApiResponse(true, "Ação social deletada com sucesso", convertToDTO(socialAction.get()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
