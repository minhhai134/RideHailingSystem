package EP.RideHailingSystem.repository;

import EP.RideHailingSystem.model.Client;
import EP.RideHailingSystem.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {

    Optional<Client> findByUserNameAndPassWord(String userName, String passWord);
}
