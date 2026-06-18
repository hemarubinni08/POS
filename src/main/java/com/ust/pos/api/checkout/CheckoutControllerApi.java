package com.ust.pos.api.checkout;

import com.ust.pos.checkout.service.CheckoutService;
import com.ust.pos.dto.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutControllerApi {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/{cartId}")
    public ResponseEntity<InvoiceDto> checkout(@PathVariable String cartId,
                                               @RequestParam(required = false) String paymentReference) {
        InvoiceDto invoice = checkoutService.checkout(cartId, paymentReference);
        if (!invoice.isSuccess()) {
            return ResponseEntity.badRequest().body(invoice);
        }
        return ResponseEntity.ok(invoice);
    }
}