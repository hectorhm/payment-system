package br.com.payment_system.notification;

import br.com.payment_system.transaction.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static br.com.payment_system.utils.Constants.TRANSACTION_NOTIFICATION;

@Service
public class NotificationConsumer {

    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("").build();
    }

    @KafkaListener(topics = TRANSACTION_NOTIFICATION, groupId = "payment-system")
    public void receiveNotification(Transaction transaction) {
        var response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);

        if (response.getStatusCode().isError() || !response.getBody().message()) {
            throw new NotificationException("Error sending notification");
        }
    }
}
