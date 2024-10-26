package EP.RideHailingSystem.dto.WebSocket;

import EP.RideHailingSystem.model.Fare;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideMatchingNotification {
    private Fare fare;
}
