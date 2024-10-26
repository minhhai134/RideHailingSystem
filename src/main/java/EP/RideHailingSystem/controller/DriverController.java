package EP.RideHailingSystem.controller;

import EP.RideHailingSystem.dto.Rest.*;
import EP.RideHailingSystem.model.Driver;
import EP.RideHailingSystem.service.DriverService;
import EP.RideHailingSystem.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/driver")
@CrossOrigin // De tam thoi
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private LocationService locationService;

    @PostMapping("/login")
    public ResponseEntity<DriverLoginResponse> login(@Valid @RequestBody DriverLoginRequest request){

        Driver driver = driverService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(DriverLoginResponse.builder().id(driver.getId()).build());
    }


    @PostMapping("/update-location")
    private ResponseEntity<UpdateDriverLocationResponse> updateDriverLocation(@Valid @RequestBody UpdateDriverLocationRequest request){
        locationService.updateDriverLocation(request);
        return ResponseEntity.ok().body(UpdateDriverLocationResponse.builder().updateStatus("OK").build());
    }

    @PostMapping("/response-ride-matching")
    private ResponseEntity<ResponseRideMatchingResponse> responseRideMatching(@Valid @RequestBody ResponseRideMatchingRequest request) {
        driverService.responseRideMatching(request);
        return ResponseEntity.ok(ResponseRideMatchingResponse.builder().status("Response sent").build());
    }



    
}
