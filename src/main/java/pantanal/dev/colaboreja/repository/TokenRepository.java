package pantanal.dev.colaboreja.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pantanal.dev.colaboreja.model.TokenModel;

//import ch.qos.logback.core.subst.Token;

public interface TokenRepository extends JpaRepository<TokenModel, Integer> {

    @Query(value = """
      select t from TokenModel t inner join UserModel u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<TokenModel> findAllValidTokenByUser(Integer id);

    Optional<TokenModel> findByToken(String token);
}