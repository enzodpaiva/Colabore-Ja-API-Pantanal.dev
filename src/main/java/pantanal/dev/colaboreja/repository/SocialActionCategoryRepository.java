package pantanal.dev.colaboreja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pantanal.dev.colaboreja.model.SocialActionCategoryModel;
import pantanal.dev.colaboreja.model.SocialActionModel;

import java.util.List;

public interface SocialActionCategoryRepository extends JpaRepository<SocialActionCategoryModel, Long> {
    public List<SocialActionCategoryModel> findByNameContainingIgnoreCase(String texto);
}
