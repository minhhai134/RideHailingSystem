package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.ClientLoginRequest;
import EP.RideHailingSystem.dto.DriverLoginRequest;
import EP.RideHailingSystem.model.Client;
import EP.RideHailingSystem.model.Driver;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {

    Client login(ClientLoginRequest request);
}
