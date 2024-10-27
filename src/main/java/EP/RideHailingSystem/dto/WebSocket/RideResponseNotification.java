package EP.RideHailingSystem.dto.WebSocket;

import EP.RideHailingSystem.model.Driver;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideResponseNotification {
    private Driver driver;
    private String response;
}
