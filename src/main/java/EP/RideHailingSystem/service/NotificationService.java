package EP.RideHailingSystem.service;

import EP.RideHailingSystem.model.Driver;
import EP.RideHailingSystem.model.Fare;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    void sendRideMatchingNotification(List<String> driverId, Fare fare);

    void sendRideResponseNotification(String userId, Driver driver);
}
