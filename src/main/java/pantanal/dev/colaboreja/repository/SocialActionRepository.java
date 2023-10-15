package pantanal.dev.colaboreja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pantanal.dev.colaboreja.model.SocialActionModel;

import java.util.List;

public interface SocialActionRepository extends JpaRepository<SocialActionModel, Long> {
    public List<SocialActionModel> findByNameContainingIgnoreCase(String texto);
}
