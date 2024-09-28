package br.com.payment_system;

import br.com.payment_system.utils.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.kafka.config.TopicBuilder;

import static br.com.payment_system.utils.Constants.TRANSACTION_NOTIFICATION;

@EnableJdbcAuditing
@SpringBootApplication
public class PaymentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSystemApplication.class, args);
	}

	@Bean
	NewTopic notificationTopic() {
		return TopicBuilder.name(TRANSACTION_NOTIFICATION).build();
	}

}
