package pantanal.dev.colaboreja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pantanal.dev.colaboreja.model.SocialActionContractModel;

import java.util.List;

public interface SocialActionContractRepository extends JpaRepository<SocialActionContractModel, Long> {
    public List<SocialActionContractModel> findByKeyContractContainingIgnoreCase(String keyContract);
}
