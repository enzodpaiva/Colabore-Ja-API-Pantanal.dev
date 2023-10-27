package pantanal.dev.colaboreja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pantanal.dev.colaboreja.enumerable.SocialActionContractStatusEnum;
import pantanal.dev.colaboreja.model.SocialActionContractModel;
import pantanal.dev.colaboreja.model.SocialActionModel;
import pantanal.dev.colaboreja.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface SocialActionContractRepository extends JpaRepository<SocialActionContractModel, Long> {
    public List<SocialActionContractModel> findByKeyProcessContainingIgnoreCase(String keyProcess);
    public List<SocialActionContractModel> findByKeyDocumentContainingIgnoreCase(String keyDocument);
//    public List<SocialActionContractModel> findByStatusContractContainingIgnoreCase(SocialActionContractStatusEnum statusContract);
//    public List<SocialActionContractModel> findBySocialActionIdContainingIgnoreCase(String socialActionId);
//    public List<SocialActionContractModel> findByColaboratorContainingIgnoreCase(String colaborator);

    public Optional<SocialActionContractModel> findByColaboratorIdAndSocialActionIdId(Integer colaborator, Long socialAction);

    List<SocialActionContractModel> findByStatusContract(SocialActionContractStatusEnum statusContract);


}
