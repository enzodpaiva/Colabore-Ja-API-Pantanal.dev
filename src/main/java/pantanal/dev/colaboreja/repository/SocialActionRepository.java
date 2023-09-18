package pantanal.dev.colaboreja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pantanal.dev.colaboreja.model.SocialActionModel;

public interface SocialActionRepository extends JpaRepository<SocialActionModel, Long> {
    
}
