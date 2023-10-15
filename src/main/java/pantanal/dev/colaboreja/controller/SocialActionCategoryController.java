package pantanal.dev.colaboreja.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pantanal.dev.colaboreja.DTO.SocialActionCategoryDTO;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.service.SocialActionCategoryService;
import pantanal.dev.colaboreja.util.ApiResponse;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/social-action-category")
public class SocialActionCategoryController {
    @Autowired
    private SocialActionCategoryService service;

    private SocialActionCategoryDTO convertToDTO(SocialActionCategoryModel socialActionCategory) {
        return SocialActionCategoryDTO.builder()
                .id(socialActionCategory.getId())
                .name(socialActionCategory.getName())
                .build();
    }

    private SocialActionCategoryModel convertToEntity(SocialActionCategoryDTO socialActionCategory) {
        return SocialActionCategoryModel.builder()
                .id(socialActionCategory.getId())
                .name(socialActionCategory.getName())
                .build();
    }

    @GetMapping
    public Collection<SocialActionCategoryDTO> getAllSocialActionCategory(
            @Parameter(description = "Nome da categoria da ação social")
            @RequestParam(value = "name", required = false) String texto) {

        try {
            return this.service.findSocialActionCategory(texto).stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getSocialActionCategory(
            @Parameter(description = "identificador") @PathVariable(value = "id", required = true) Long id) {
        try {
            var socialActionCategory = this.service.getSocialActionCategoryById(id);
            return ResponseEntity.ok(convertToDTO(socialActionCategory.get()));
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<?> createSocialActionCategory(@Valid @RequestBody SocialActionCategoryDTO socialActionCategory) {

        try {
            var result = this.service.createSocialActionCategory(convertToEntity(socialActionCategory));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(result));
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSocialActionCategory(
            @PathVariable Long id,
            @Valid @RequestBody SocialActionCategoryDTO socialAction) {

        try {
            var existingSocialActionCategory = this.service.updateSocialActionCategory(id, convertToEntity(socialAction));

            ApiResponse response = new ApiResponse(true, "Categoria de ação social atualizada com sucesso", convertToDTO(existingSocialActionCategory));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSocialActionCategory(@PathVariable(value = "id", required = true) Long id) {

        try {
            var socialAction = this.service.getSocialActionCategoryById(id);
            this.service.deleteSocialActionCategoryById(socialAction.get().getId());

            ApiResponse response = new ApiResponse(true, "Categoria de ação social deletada com sucesso", convertToDTO(socialAction.get()));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw ex;
        }
    }


}
