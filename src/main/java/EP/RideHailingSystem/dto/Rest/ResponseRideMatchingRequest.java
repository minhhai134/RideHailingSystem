package EP.RideHailingSystem.dto.Rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseRideMatchingRequest {
    private String fareId;

    private String riderId;

    private String driverId;

    private String accept;

}
