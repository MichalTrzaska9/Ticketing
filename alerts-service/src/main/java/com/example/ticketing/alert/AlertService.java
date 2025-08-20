package com.example.ticketing.alert;

import com.example.ticketing.purchase.event.PurchasePlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertService {

    private final JavaMailSender mailSender;

    @KafkaListener(topics = "purchase-placed")
    public void handlePurchaseEvent(PurchasePlacedEvent purchasePlacedEvent) {
        log.info("Received message from purchase-placed topic {}", purchasePlacedEvent);
        MimeMessagePreparator email = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("eventplxxx@gmail.com");
            helper.setTo(purchasePlacedEvent.getEmail().toString());
            helper.setSubject(String.format("Purchase Confirmation #%s", purchasePlacedEvent.getPurchaseNumber()));
            helper.setText(String.format("""
                            Hello %s,

                            Your order with number: %s has been successfully placed.
                                                       
                            Thank you!
                                                        
                            Best Regards
                            Event PL Team
                            """,
                    purchasePlacedEvent.getFirstName(),
                    purchasePlacedEvent.getPurchaseNumber()));
        };
        try {
            mailSender.send(email);
            log.info("Confirmation email sent successfully.");
        } catch (MailException e) {
            log.error("Error occurred while sending confirmation email", e);
            throw new RuntimeException("Failed to send purchase confirmation", e);
        }

    }
}
