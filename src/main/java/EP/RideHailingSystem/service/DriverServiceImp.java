package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.Rest.DriverLoginRequest;
import EP.RideHailingSystem.dto.Rest.ResponseRideMatchingRequest;
import EP.RideHailingSystem.exception.DriverNotFoundException;
import EP.RideHailingSystem.model.Driver;
import EP.RideHailingSystem.model.Ride;
import EP.RideHailingSystem.repository.DriverRepository;
import EP.RideHailingSystem.repository.RideRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DriverServiceImp implements DriverService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private NotificationService notificationService;

    public Driver login(DriverLoginRequest request) {
        return driverRepository.findByUserNameAndPassWord(request.getUserName(), request.getPassWord())
                .orElseThrow(() -> new DriverNotFoundException());
    }

    public void responseRideMatching(ResponseRideMatchingRequest request) {
        if(request.getAccept().equals("accept")){
         Ride savedRide = rideRepository.save(Ride.builder()
                            .fareId(request.getFareId())
                            .driverId(request.getDriverId())
                            .build());

         Driver driver = driverRepository.findById(request.getDriverId()).orElseThrow(()-> new DriverNotFoundException());

         try {
             notificationService.sendRideResponseNotification(request.getRiderId(), driver);
         } catch (MessagingException e) {
             throw new MessagingException("MESSAGE_SENDING_ERROR");
         }
        }

        // Tu choi chuyen di:
        else if(request.getAccept().equals("reject")) {
            try {
                notificationService.sendRideResponseNotification(request.getRiderId(), null);
            } catch (MessagingException e) {
                throw new MessagingException("MESSAGE_SENDING_ERROR");
            }
        }


    }


}
