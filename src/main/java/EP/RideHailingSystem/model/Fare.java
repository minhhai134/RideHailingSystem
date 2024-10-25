package EP.RideHailingSystem.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Entity
@Table(name = "booking")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

//    private String status; // success, canceled,... -> Dùng ENUM?

    private String clientId;

    private Instant requestTime;

    private double latPickup;

    private double longPickup;

    private double latDestination;

    private double longDestination;

//    private String driverId;

}
