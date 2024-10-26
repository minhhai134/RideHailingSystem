package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.Rest.UpdateDriverLocationRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {

    boolean updateDriverLocation(UpdateDriverLocationRequest request);

    List<String> getNearestDrivers(double long_, double lat_);
}
