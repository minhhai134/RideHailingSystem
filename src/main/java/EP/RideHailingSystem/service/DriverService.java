package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.DriverLoginRequest;
import EP.RideHailingSystem.dto.UpdateDriverLocationRequest;
import EP.RideHailingSystem.model.Driver;
import org.springframework.stereotype.Service;

@Service
public interface DriverService {

     Driver login(DriverLoginRequest request);

     boolean updateDriverLocation(UpdateDriverLocationRequest request);


}
