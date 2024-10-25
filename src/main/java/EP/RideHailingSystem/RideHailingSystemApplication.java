package EP.RideHailingSystem;

import EP.RideHailingSystem.model.Client;
import EP.RideHailingSystem.model.Driver;
import EP.RideHailingSystem.repository.ClientRepository;
import EP.RideHailingSystem.repository.DriverRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RideHailingSystemApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(RideHailingSystemApplication.class, args);

		DriverRepository driverRepository = context.getBean(DriverRepository.class);
		ClientRepository clientRepository = context.getBean(ClientRepository.class);

		driverRepository.save(Driver.builder().
				userName("driver1").
				passWord("password1").
				build());

//		driverRepository.save(Driver.builder().
//				userName("user2").
//				passWord("password2").
//				build());

		clientRepository.save(Client.builder().
				userName("client1").
				passWord("password1").
				build());

	}

}
