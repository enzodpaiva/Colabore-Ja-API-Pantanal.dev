package pantanal.dev.colaboreja.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pantanal.dev.colaboreja.DTO.SocialActionDTO;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.repository.SocialActionRepository;

@Service
public class SocialActionService {
    @Autowired
    private SocialActionRepository socialActionRepository;

    @Autowired
    private SocialActionCategoryService socialActionCategoryService;

    @Autowired
    private UserService userService;

    public SocialActionModel createSocialAction(SocialActionDTO socialAction) {

        var category = this.socialActionCategoryService.getSocialActionCategoryById(socialAction.getSocialActionCategoryId());
        var author = this.userService.getUserById(socialAction.getAuthor());
        var resultConvertEntity = this.convertToEntity(socialAction);

        resultConvertEntity.setSocialActionCategoryId(category.get());
        resultConvertEntity.setAuthor(author.get());

        return this.socialActionRepository.save(resultConvertEntity);
    }

    public List<SocialActionModel> getAllSocialActions() {
        return this.socialActionRepository.findAll();
    }
    
    
    public Optional<SocialActionModel> getSocialActionById(Long id) {
        var result = this.socialActionRepository.findById(id);

        if (!result.isPresent()){
            throw new NoSuchElementException("Ação social com o ID especificado não foi encontrada");
        }

        return result;
    }

    public List<SocialActionModel> findSocialAction(String texto) {
        if (texto == null || texto.isEmpty())
            return this.socialActionRepository.findAll();
        else
            return this.socialActionRepository.findByNameContainingIgnoreCase(texto);
    }

    public SocialActionModel updateSocialAction(Long id, SocialActionDTO socialActionDetails) {
        var category = this.socialActionCategoryService.getSocialActionCategoryById(socialActionDetails.getSocialActionCategoryId());
        var author = this.userService.getUserById(socialActionDetails.getAuthor().intValue());
        Optional<SocialActionModel> socialAction = this.socialActionRepository.findById(id);
        if (!socialAction.isPresent()) {
            throw new NoSuchElementException("Ação social com o ID especificado não foi encontrada");
        }

        SocialActionModel existingSocialAction = socialAction.get();
        existingSocialAction.setName(socialActionDetails.getName());
        existingSocialAction.setDescription(socialActionDetails.getDescription());
        existingSocialAction.setInitDateTime(socialActionDetails.getInitDateTime());
        existingSocialAction.setFinishDateTime(socialActionDetails.getFinishDateTime());
        existingSocialAction.setSocialActionCategoryId(category.get());
        existingSocialAction.setAuthor(author.get());
        existingSocialAction.setLocation(socialActionDetails.getLocation());

        return this.socialActionRepository.save(existingSocialAction);
    }

    public void deleteAllSocialAction() {
        this.socialActionRepository.deleteAll();
    }

    public void deleteSocialAction(Long id) {
        this.socialActionRepository.deleteById(id);
    }

    private SocialActionModel convertToEntity(SocialActionDTO socialAction) {
        return SocialActionModel.builder()
                .id(socialAction.getId())
                .name(socialAction.getName())
                .description(socialAction.getDescription())
                .initDateTime(socialAction.getInitDateTime())
                .finishDateTime(socialAction.getFinishDateTime())
                .location(socialAction.getLocation())
                .build();
    }

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
    // TO DO: buscar por outros campos

}
