package EP.RideHailingSystem.dto.Rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientLoginRequest {

    private String userName;

    private String passWord;
}
