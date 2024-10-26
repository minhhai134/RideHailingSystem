package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.Rest.DriverLoginRequest;
import EP.RideHailingSystem.dto.Rest.ResponseRideMatchingRequest;
import EP.RideHailingSystem.model.Driver;
import EP.RideHailingSystem.model.Ride;
import org.springframework.stereotype.Service;

@Service
public interface DriverService {

     Driver login(DriverLoginRequest request);

     void responseRideMatching(ResponseRideMatchingRequest request);

//     boolean updateDriverLocation(UpdateDriverLocationRequest request);


}
