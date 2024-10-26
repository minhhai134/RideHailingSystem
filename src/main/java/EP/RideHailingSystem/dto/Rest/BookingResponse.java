package EP.RideHailingSystem.dto.Rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {
    private String bookingStatus;
}
