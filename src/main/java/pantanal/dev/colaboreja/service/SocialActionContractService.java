package pantanal.dev.colaboreja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pantanal.dev.colaboreja.DTO.SocialActionContractDTO;
import pantanal.dev.colaboreja.DTO.SocialActionDTO;
import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.repository.SocialActionContractRepository;
import pantanal.dev.colaboreja.repository.SocialActionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class SocialActionContractService {

    @Autowired
    private SocialActionContractRepository socialActionContractRepository;

    @Autowired
    private SocialActionService socialActionService;

    public SocialActionContractModel createSocialActionContract(SocialActionContractDTO socialActionContractDTO) {
        var result = this.socialActionService.getSocialActionById(socialActionContractDTO.getSocialActionId());

        var resultConvertEntity = this.convertToEntity(socialActionContractDTO);

        resultConvertEntity.setSocialActionId(result.get());

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

    public List<SocialActionContractModel> findSocialActionContract(String keyContract) {
        if (keyContract == null || keyContract.isEmpty())
            return this.socialActionContractRepository.findAll();
        else
            return this.socialActionContractRepository.findByKeyContractContainingIgnoreCase(keyContract);
    }

    public SocialActionContractModel updateSocialActionContract(Long id, SocialActionContractDTO socialActionDetails) {
        var result = this.socialActionService.getSocialActionById(socialActionDetails.getSocialActionId());
        Optional<SocialActionContractModel> socialActionContract = this.socialActionContractRepository.findById(id);
        if (!socialActionContract.isPresent()) {
            throw new NoSuchElementException("Coontrato de ação social com o ID especificado não foi encontrada");
        }

        SocialActionContractModel existingSocialActionContract = socialActionContract.get();
        existingSocialActionContract.setKeyContract(socialActionDetails.getKeyContract());
        existingSocialActionContract.setStatusContract(SocialActionContractStatusEnum.fromString(socialActionDetails.getStatusContract()));
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
                .keyContract(socialActionContractDTO.getKeyContract())
                .statusContract(SocialActionContractStatusEnum.fromString(socialActionContractDTO.getStatusContract()))
                .build();
    }
}
