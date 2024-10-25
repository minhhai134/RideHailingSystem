package EP.RideHailingSystem.repository;

import EP.RideHailingSystem.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,String> {


    Optional<Driver> findByUserNameAndPassWord(String userName, String passWord);
}
