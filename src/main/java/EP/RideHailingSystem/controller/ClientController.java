package EP.RideHailingSystem.controller;

import EP.RideHailingSystem.dto.Rest.BookingRequest;
import EP.RideHailingSystem.dto.Rest.BookingResponse;
import EP.RideHailingSystem.dto.Rest.ClientLoginRequest;
import EP.RideHailingSystem.dto.Rest.DriverLoginResponse;
import EP.RideHailingSystem.model.Client;
import EP.RideHailingSystem.service.ClientService;
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
@RequestMapping("/api/client")
@CrossOrigin // De tam thoi
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/login")
    public ResponseEntity<DriverLoginResponse> login(@Valid @RequestBody ClientLoginRequest request){

        Client client = clientService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(DriverLoginResponse.builder().id(client.getId()).build());
    }

    @PostMapping("/booking")
    public ResponseEntity<BookingResponse> booking(@Valid @RequestBody BookingRequest request) {
        // ? Goi LocationService de tim tai xe gan nhat
        // Sau do lam sao de gui thong bao cho cac tai xe
        clientService.booking(request);
        return ResponseEntity.status(HttpStatus.OK).body(BookingResponse.builder().bookingStatus("requested").build());
    }


}
