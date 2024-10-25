package EP.RideHailingSystem.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    // Define matchingQueue
    @Bean
    public Queue matchingQueue(){
        return QueueBuilder.durable("matchingQueue")
//                .withArgument("x-dead-letter-exchange", "email-dlx-exchange") // Forward failed messages to DLX
//                .withArgument("x-dead-letter-routing-key", "email-dlx-routing-key")
                .withArgument("x-message-ttl", 60000)
                .build(); // Time to live for the messages in the original queue (optional)
    }

    // Define the exchange for email queue
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("matching-exchange");
    }

    // Biding matchingQueue to exchange by routing key
    @Bean
    public Binding matchingQueueBinding(Queue matchingQueue, DirectExchange exchange) {
        return BindingBuilder.bind(matchingQueue).to(exchange).with("matching-routing-key");
    }



}
