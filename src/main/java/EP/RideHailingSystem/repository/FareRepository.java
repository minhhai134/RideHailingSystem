package EP.RideHailingSystem.repository;

import EP.RideHailingSystem.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare, String> {
}
