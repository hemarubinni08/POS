package com.ust.pos.checkout.service;

import com.ust.pos.dto.InvoiceDto;

public interface CheckoutService {
    InvoiceDto checkout(String cartId, String paymentReference);
}