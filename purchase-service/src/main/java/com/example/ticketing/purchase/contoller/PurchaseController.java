package com.example.ticketing.purchase.contoller;

import com.example.ticketing.purchase.dto.PurchaseRequest;
import com.example.ticketing.purchase.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String makePurchase(@RequestBody PurchaseRequest purchaseRequest) {
        purchaseService.makePurchase(purchaseRequest);
        return "Purchase Completed Successfully";
    }
}
