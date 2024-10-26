package EP.RideHailingSystem.service;

import EP.RideHailingSystem.model.Driver;
import EP.RideHailingSystem.model.Fare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationServiceImp implements NotificationService{

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendRideMatchingNotification(List<String> driverId, Fare fare){
        String id = driverId.get(0);
        try {
            log.info("Choosen driver: {}", id);
            messagingTemplate.convertAndSend("/topic/"+id, fare);
        } catch (MessagingException e) {
            throw new MessagingException("MESSAGE_SENDING_ERROR");
        }

    }

    public void sendRideResponseNotification(String userId, Driver driver) {
        try {
            log.info("Driver's response: {}", driver);
            if(driver==null) messagingTemplate.convertAndSend("/topic/"+userId, "Not matching ride");
            messagingTemplate.convertAndSend("/topic/"+userId, driver);
        } catch (MessagingException e) {
            throw new MessagingException("MESSAGE_SENDING_ERROR");
        }
    }

}
