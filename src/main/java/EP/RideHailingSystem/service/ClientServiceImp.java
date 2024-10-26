package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.Rest.BookingRequest;
import EP.RideHailingSystem.dto.Rest.ClientLoginRequest;
import EP.RideHailingSystem.exception.ClientNotFoundException;
import EP.RideHailingSystem.model.Client;
import EP.RideHailingSystem.model.Fare;
import EP.RideHailingSystem.repository.ClientRepository;
import EP.RideHailingSystem.repository.FareRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientServiceImp implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private FareRepository fareRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Client login(ClientLoginRequest request) {
        return clientRepository.findByUserNameAndPassWord(request.getUserName(), request.getPassWord())
                .orElseThrow(() -> new ClientNotFoundException());
    }

    public void booking(BookingRequest request) {
        Fare savedFare = fareRepository.save(Fare.builder()
                        .clientId(request.getClientId())
                        .latPickup(request.getLatPickup())
                        .longPickup(request.getLongPickup())
                        .latDestination(request.getLatDestination())
                        .longDestination(request.getLongDestination())
                        .requestTime(request.getRequestTime())
                        .build());

        List<String> nearestDrivers = locationService.getNearestDrivers(request.getLongPickup(), request.getLatPickup());
        log.info("Fare: {}", savedFare.getId());
//        log.info("Nearest Drivers: {}", nearestDrivers );
        try {
            notificationService.sendRideMatchingNotification(nearestDrivers,savedFare);
        } catch (MessagingException e) {
            throw new MessagingException("MESSAGE_SENDING_ERROR");
        }
//        return nearestDrivers;
    }

}
