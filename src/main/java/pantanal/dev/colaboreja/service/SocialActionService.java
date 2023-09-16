package pantanal.dev.colaboreja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.repository.SocialActionRepository;

@Service
public class SocialActionService {
    @Autowired
    private SocialActionRepository socialActionRepository;

    public SocialActionModel createSocialAction(SocialActionModel socialAction) {
        return socialActionRepository.save(socialAction);
    }

    public List<SocialActionModel> getAllSocialActions() {
        return socialActionRepository.findAll();
    }
    
    
    public Optional<SocialActionModel> getSocialActionById(Long id) {
        return socialActionRepository.findById(id);
    }

    public SocialActionModel updateSocialAction(Long id, SocialActionModel socialActionDetails) {
        Optional<SocialActionModel> socialAction = socialActionRepository.findById(id);
        if (socialAction.isPresent()) {
            SocialActionModel existingSocialAction = socialAction.get();
            existingSocialAction.setName(socialActionDetails.getName());
            existingSocialAction.setDescription(socialActionDetails.getDescription());

            return socialActionRepository.save(existingSocialAction);
        }

        return null; 
    }

    public void deleteAllSocialAction() {
        socialActionRepository.deleteAll();
    }

    public void deleteSocialAction(Long id) {
        socialActionRepository.deleteById(id);
    }

    // TO DO: tratamento de erro
    // TO DO: buscar por outros campos

}
