package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.UpdateDriverLocationRequest;
import EP.RideHailingSystem.exception.InvalidRequestException;
import EP.RideHailingSystem.exception.UpdateLocationErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.lettuce.core.GeoArgs.Unit.km;

@Service
@Slf4j
public class LocationServiceImp implements LocationService {
    // Hien tai chuc nang luu vi tri tai xe dang duoc dat o DriverService, can xem xet lai
    private static final int limitNearest = 2;
    private static final double radiusInKm = 1;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    public boolean updateDriverLocation(UpdateDriverLocationRequest request) {
        String id = request.getId();
        double lat_ = request.getLat_();
        double long_ = request.getLong_();
        String status = request.getStatus();


        if(status.equals("online")){ // Bad design
            long count = redisTemplate.opsForGeo().add("driverLocation", new Point(long_, lat_), id);
//            log.info("Position: {}", redisTemplate.opsForGeo().position("driverLocation", id));
            if(count==1) return true;
            else throw new UpdateLocationErrorException();
        }
        else if(status.equals("normal")){
            long count = redisTemplate.opsForGeo().add("driverLocation", new Point(lat_,long_), id);
            if(count==0) return true;
            else throw new UpdateLocationErrorException();
        }
        else if(status.equals("offline")){
            long count = redisTemplate.opsForZSet().remove("driverLocation", id);
            if(count==1) return true;
            else throw new UpdateLocationErrorException();
        }
        else throw new InvalidRequestException();

    }


    public List<String> getNearestDrivers(double long_, double lat_) {

        // Use GeoRadiusCommandArgs to include distance and limit results
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance() // Include distance in results
                .includeCoordinates()
                .limit(limitNearest)      // Limit the number of results
                .sortAscending();  // Sort results by distance (ascending)

        Circle circle = new Circle(new Point(long_, lat_)
                ,new Distance(radiusInKm, Metrics.KILOMETERS));

        List<String> nearestDrivers = new ArrayList<>();

        redisTemplate.opsForGeo().radius("driverLocation", circle, args)
                .getContent().stream().forEach(result -> {
                    RedisGeoCommands.GeoLocation<String> location = result.getContent();
                    nearestDrivers.add(location.getName());
                });

        return nearestDrivers;
    }
}
