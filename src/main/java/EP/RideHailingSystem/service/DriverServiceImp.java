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


}
