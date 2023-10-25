package pantanal.dev.colaboreja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pantanal.dev.colaboreja.DTO.SocialActionContractDTO;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.model.UserModel;
import pantanal.dev.colaboreja.repository.SocialActionContractRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SocialActionContractService {

    @Autowired
    private SocialActionContractRepository socialActionContractRepository;

    @Autowired
    private SocialActionService socialActionService;

    @Autowired
    private UserService userService;

    public SocialActionContractModel createSocialActionContract(SocialActionContractDTO socialActionContractDTO) {
        var socialAction = this.socialActionService.getSocialActionById(socialActionContractDTO.getSocialActionId());
        var colaborator = this.userService.getUserById(socialActionContractDTO.getColaborator());
        var resultConvertEntity = this.convertToEntity(socialActionContractDTO);

        resultConvertEntity.setSocialActionId(socialAction.get());
        resultConvertEntity.setColaborator(colaborator.get());

        return this.socialActionContractRepository.save(resultConvertEntity);
    }

    public List<SocialActionContractModel> getAllSocialActionContracts() {
        return this.socialActionContractRepository.findAll();
    }

    public Optional<SocialActionContractModel> getSocialActionContractById(Long id) {
        var result = this.socialActionContractRepository.findById(id);

        if (!result.isPresent()){
            throw new NoSuchElementException("Contrato de ação social com o ID especificado não foi encontrada");
        }

        return result;
    }

    public List<SocialActionContractModel> findSocialActionContract(String keyProcess) {
        if (keyProcess == null || keyProcess.isEmpty())
            return this.socialActionContractRepository.findAll();
        else
            return this.socialActionContractRepository.findByKeyProcessContainingIgnoreCase(keyProcess);
    }

    public SocialActionContractModel findContractByUserAndSocialAction(Integer colaborator, Long socialAction) {
        Optional<SocialActionContractModel> result = this.socialActionContractRepository.findByColaboratorIdAndSocialActionIdId(colaborator,socialAction);

        if (!result.isPresent()) {
            throw new NoSuchElementException("Contrato de ação social com o ID de colaborador ou ação social especificado não foi encontrado");
        }

        return result.get();
    }

    public SocialActionContractModel updateSocialActionContract(Long id, SocialActionContractDTO socialActionDetails) {
        var result = this.socialActionService.getSocialActionById(socialActionDetails.getSocialActionId());
        Optional<SocialActionContractModel> socialActionContract = this.socialActionContractRepository.findById(id);
        if (!socialActionContract.isPresent()) {
            throw new NoSuchElementException("Contrato de ação social com o ID especificado não foi encontrada");
        }

        SocialActionContractModel existingSocialActionContract = socialActionContract.get();
        existingSocialActionContract.setKeyProcess(socialActionDetails.getKeyProcess());
        existingSocialActionContract.setKeyDocument(socialActionDetails.getKeyDocument());
        existingSocialActionContract.setStatusContract(socialActionDetails.getStatusContract());
        existingSocialActionContract.setSocialActionId(result.get());

        return this.socialActionContractRepository.save(existingSocialActionContract);
    }

    public void deleteAllSocialActionContract() {
        this.socialActionContractRepository.deleteAll();
    }

    public void deleteSocialActionContract(Long id) {
        this.socialActionContractRepository.deleteById(id);
    }

    private SocialActionContractModel convertToEntity(SocialActionContractDTO socialActionContractDTO) {
        return SocialActionContractModel.builder()
                .id(socialActionContractDTO.getId())
                .keyProcess(socialActionContractDTO.getKeyProcess())
                .keyDocument(socialActionContractDTO.getKeyDocument())
                .statusContract(socialActionContractDTO.getStatusContract())
                .build();
    }

    public SocialActionContractModel saveProcessColaborator(String idProcess, SocialActionContractModel socialActionContract) {

        socialActionContract.setKeyProcess(idProcess);

        return this.socialActionContractRepository.save(socialActionContract);
    }
}
