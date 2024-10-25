package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.BookingRequest;
import EP.RideHailingSystem.dto.ClientLoginRequest;
import EP.RideHailingSystem.exception.ClientNotFoundException;
import EP.RideHailingSystem.exception.DriverNotFoundException;
import EP.RideHailingSystem.model.Client;
import EP.RideHailingSystem.model.Fare;
import EP.RideHailingSystem.repository.ClientRepository;
import EP.RideHailingSystem.repository.FareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImp implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private FareRepository fareRepository;

    @Autowired
    private LocationService locationService;

    @Override
    public Client login(ClientLoginRequest request) {
        return clientRepository.findByUserNameAndPassWord(request.getUserName(), request.getPassWord())
                .orElseThrow(() -> new ClientNotFoundException());
    }

    public void booking(BookingRequest request){
        Fare savedFare = fareRepository.save(Fare.builder()
                        .clientId(request.getClientId())
                        .latPickup(request.getLatPickup())
                        .longPickup(request.getLongPickup())
                        .latDestination(request.getLatDestination())
                        .longDestination(request.getLongDestination())
                        .requestTime(request.getRequestTime())
                        .build());


    }

}
