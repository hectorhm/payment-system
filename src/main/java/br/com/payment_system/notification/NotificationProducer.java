package br.com.payment_system.notification;

import br.com.payment_system.transaction.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static br.com.payment_system.utils.Constants.TRANSACTION_NOTIFICATION;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Transaction transaction) {

        kafkaTemplate.send(TRANSACTION_NOTIFICATION, transaction);
    }
}
