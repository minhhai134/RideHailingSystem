package EP.RideHailingSystem.dto.Rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDriverLocationRequest {
    private String id;

    private double lat_;

    private double long_;

    private String status;
}
