package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.Rest.BookingRequest;
import EP.RideHailingSystem.dto.Rest.ClientLoginRequest;
import EP.RideHailingSystem.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    Client login(ClientLoginRequest request);

    void booking(BookingRequest request);
}
