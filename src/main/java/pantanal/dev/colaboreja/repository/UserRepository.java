package pantanal.dev.colaboreja.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import pantanal.dev.colaboreja.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByEmail(String email);

}