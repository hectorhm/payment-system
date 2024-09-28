package br.com.payment_system.notification;

import br.com.payment_system.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationProducer producer;

    public NotificationService(NotificationProducer producer) {
        this.producer = producer;
    }

    public void notify(Transaction transaction) {
        producer.sendNotification(transaction);
    }

}
