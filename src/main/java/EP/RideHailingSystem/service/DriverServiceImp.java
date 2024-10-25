package EP.RideHailingSystem.service;

import EP.RideHailingSystem.dto.DriverLoginRequest;
import EP.RideHailingSystem.dto.UpdateDriverLocationRequest;
import EP.RideHailingSystem.exception.DriverNotFoundException;
import EP.RideHailingSystem.exception.InvalidRequestException;
import EP.RideHailingSystem.exception.UpdateLocationErrorException;
import EP.RideHailingSystem.model.Driver;
import EP.RideHailingSystem.repository.DriverRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DriverServiceImp implements DriverService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private DriverRepository repository;

    public Driver login(DriverLoginRequest request) {
        return repository.findByUserNameAndPassWord(request.getUserName(), request.getPassWord())
                .orElseThrow(() -> new DriverNotFoundException());
    }


    public boolean updateDriverLocation(UpdateDriverLocationRequest request) {
        String id = request.getId();
        double lat_ = request.getLat_();
        double long_ = request.getLong_();
        String status = request.getStatus();


        if(status.equals("online")){ // Bad design
           long count = redisTemplate.opsForGeo().add("driverLocation", new Point(lat_,long_), id);
           log.info("Position: {}", redisTemplate.opsForGeo().position("driverLocation", id));
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
}
