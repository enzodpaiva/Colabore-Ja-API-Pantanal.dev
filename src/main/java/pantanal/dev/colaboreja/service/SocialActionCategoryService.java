package pantanal.dev.colaboreja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.repository.SocialActionCategoryRepository;

import java.util.*;

@Service
public class SocialActionCategoryService {

    @Autowired
    private SocialActionCategoryRepository socialActionCategoryRepository;

    public SocialActionCategoryModel createSocialActionCategory(SocialActionCategoryModel socialAction) {

        var categoryName = socialAction.getName().trim();
        categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1).toLowerCase();
        socialAction.setName(categoryName);
        var count = findSocialActionCategory(socialAction.getName());

        if (!count.isEmpty()) {
            throw new DuplicateKeyException("Categoria de ação social já cadastrada");
        }

        return this.socialActionCategoryRepository.save(socialAction);
    }

    public List<SocialActionCategoryModel> getAllSocialActionCategorys() {
        return this.socialActionCategoryRepository.findAll();
    }

    public Optional<SocialActionCategoryModel> getSocialActionCategoryById(Long id) {

        var result = this.socialActionCategoryRepository.findById(id);

        if (!result.isPresent()){
            throw new NoSuchElementException("Categoria de ação social com o ID especificado não foi encontrada");
        }

        return result;
    }

    public List<SocialActionCategoryModel> findSocialActionCategory(String texto) {
        if (texto == null || texto.isEmpty())
            return this.socialActionCategoryRepository.findAll();
        else
            return this.socialActionCategoryRepository.findByNameContainingIgnoreCase(texto);
    }

    public SocialActionCategoryModel updateSocialActionCategory(Long id, SocialActionCategoryModel socialActionDetails) {
        Optional<SocialActionCategoryModel> socialAction = this.socialActionCategoryRepository.findById(id);
        if (socialAction.isPresent()) {
            SocialActionCategoryModel existingSocialActionCategory = socialAction.get();
            var categoryName = socialActionDetails.getName().trim();
            categoryName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1).toLowerCase();
            socialActionDetails.setName(categoryName);
            var count = findSocialActionCategory(socialActionDetails.getName());

            if (!count.isEmpty()) {
                throw new DuplicateKeyException("Categoria de ação social já cadastrada");
            }

            existingSocialActionCategory.setName(socialActionDetails.getName());
            return this.socialActionCategoryRepository.save(existingSocialActionCategory);
        }

        throw new NoSuchElementException("Categoria não encontrada");
    }

    public void deleteAllSocialActionCategorys() {
        this.socialActionCategoryRepository.deleteAll();
    }

    public void deleteSocialActionCategoryById(Long id) {
        this.socialActionCategoryRepository.deleteById(id);
    }
}
