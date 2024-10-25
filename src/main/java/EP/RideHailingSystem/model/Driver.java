package EP.RideHailingSystem.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "driver")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userName;

    private String passWord;

}
