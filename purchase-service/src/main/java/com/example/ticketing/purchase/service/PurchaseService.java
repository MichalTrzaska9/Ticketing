package com.example.ticketing.purchase.service;

import com.example.ticketing.purchase.client.StockClient;
import com.example.ticketing.purchase.dto.PurchaseRequest;
import com.example.ticketing.purchase.event.PurchasePlacedEvent;
import com.example.ticketing.purchase.model.Purchase;
import com.example.ticketing.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);
    private static final String PURCHASE_TOPIC = "purchase-placed";

    private final StockClient stockClient;
    private final PurchaseRepository purchaseRepository;
    private final KafkaTemplate<String, PurchasePlacedEvent> kafkaTemplate;

    public void makePurchase(PurchaseRequest request) {
        validateRequest(request);

        if (!isTicketAvailable(request)) {
            log.warn("No tickets available for stockCode={}", request.stockCode());
            throw new RuntimeException("No tickets available");
        }

        reduceStock(request);

        Purchase purchase = savePurchase(request);
        sendPurchaseEvent(request, purchase);

        log.info("Purchase flow completed successfully: {}", purchase.getPurchaseNumber());
    }

    private void validateRequest(PurchaseRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("PurchaseRequest cannot be null");
        }
        if (request.stockCode() == null) {
            throw new IllegalArgumentException("StockCode cannot be null");
        }
    }

    private boolean isTicketAvailable(PurchaseRequest request) {
        try {
            return stockClient.isAvailable(request.stockCode(), request.quantity());
        } catch (Exception e) {
            log.error("Error while checking ticket availability", e);
            throw new RuntimeException("Error checking ticket availability", e);
        }
    }

    private void reduceStock(PurchaseRequest request) {
        try {
            stockClient.reduceStock(request.stockCode(), request.quantity());
            log.debug("Stock reduced for stockCode={} by {}", request.stockCode(), request.quantity());
        } catch (Exception e) {
            log.error("Error reducing stock", e);
            throw new RuntimeException("Error reducing stock", e);
        }
    }

    private Purchase savePurchase(PurchaseRequest request) {
        Purchase purchase = new Purchase();
        purchase.setPurchaseNumber(UUID.randomUUID().toString());
        purchase.setPrice(request.price());
        purchase.setStockCode(request.stockCode());
        purchase.setQuantity(request.quantity());

        purchaseRepository.save(purchase);
        log.debug("Purchase saved: {}", purchase);

        return purchase;
    }

    private void sendPurchaseEvent(PurchaseRequest request, Purchase purchase) {
        if (request.userDetails() == null) {
            log.warn("User details missing for purchase {}", purchase.getPurchaseNumber());
            return;
        }

        PurchasePlacedEvent event = new PurchasePlacedEvent();
        event.setPurchaseNumber(purchase.getPurchaseNumber());
        event.setEmail(request.userDetails().email());
        event.setFirstName(request.userDetails().firstName());
        event.setlastName(request.userDetails().lastName());

        try {
            kafkaTemplate.send(PURCHASE_TOPIC, event);
            log.debug("PurchasePlacedEvent sent to Kafka: {}", event);
        } catch (Exception e) {
            log.error("Error while sending event to Kafka", e);
            throw new RuntimeException("Error sending purchase event", e);
        }
    }
}
