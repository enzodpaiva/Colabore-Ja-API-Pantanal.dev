package pantanal.dev.colaboreja.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import pantanal.dev.colaboreja.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}