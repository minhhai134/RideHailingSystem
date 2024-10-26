package EP.RideHailingSystem.dto.Rest;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class BookingRequest {
    private String clientId;

    private Instant requestTime;

    private double latPickup;

    private double longPickup;

    private double latDestination;

    private double longDestination;
}
