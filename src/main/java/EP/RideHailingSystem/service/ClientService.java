package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.BookingRequest;
import EP.RideHailingSystem.dto.ClientLoginRequest;
import EP.RideHailingSystem.dto.DriverLoginRequest;
import EP.RideHailingSystem.model.Client;
import EP.RideHailingSystem.model.Driver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    Client login(ClientLoginRequest request);

    List<String> booking(BookingRequest request);
}
