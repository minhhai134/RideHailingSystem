package EP.RideHailingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LocationServiceImp implements LocationService {
    // Hien tai chuc nang luu vi tri tai xe dang duoc dat o DriverService, can xem xet lai

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public Set<String> getNearestDrivers(){


        return null;
    }
}
