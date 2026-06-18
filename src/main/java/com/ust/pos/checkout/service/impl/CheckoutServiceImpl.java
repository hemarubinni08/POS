package com.ust.pos.checkout.service.impl;

import com.ust.pos.cart.service.CartService;
import com.ust.pos.cartentry.service.CartEntryService;
import com.ust.pos.checkout.service.CheckoutService;
import com.ust.pos.dto.CartDto;
import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.dto.InvoiceDto;
import com.ust.pos.dto.OrderDto;
import com.ust.pos.dto.OrderEntryDto;
import com.ust.pos.invoice.service.InvoiceService;
import com.ust.pos.order.service.OrderService;
import com.ust.pos.orderentry.service.OrderEntryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartEntryService cartEntryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderEntryService orderEntryService;

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public InvoiceDto checkout(String cartId, String paymentReference) {

        List<CartEntryDto> cartEntries = cartEntryService.findByCartId(cartId);

        if (cartEntries == null || cartEntries.isEmpty()) {
            InvoiceDto failed = new InvoiceDto();
            failed.setSuccess(false);
            failed.setMessage("Cart is empty or does not exist: " + cartId);
            return failed;
        }

        CartDto cart = cartService.findByIdentifier(cartId);

        String orderId = "ORD-" + UUID.randomUUID();
        orderService.save(orderId);

        List<OrderEntryDto> orderEntryDtos = cartEntries.stream().map(cartEntry -> {
            OrderEntryDto orderEntryDto = new OrderEntryDto();
            orderEntryDto.setOrderId(orderId);
            orderEntryDto.setProductId(cartEntry.getProductId());
            orderEntryDto.setProductName(cartEntry.getProductName());
            orderEntryDto.setQuantity(cartEntry.getQuantity());
            orderEntryDto.setMrp(cartEntry.getMrp());
            orderEntryDto.setSellingPrice(cartEntry.getSellingPrice());
            orderEntryDto.setOriginalPrice(cartEntry.getOriginalPrice());
            orderEntryDto.setTotalPrice(cartEntry.getTotalPrice());
            orderEntryDto.setDiscount(cartEntry.getDiscount());
            return orderEntryDto;
        }).collect(Collectors.toList());

        // Bulk copy — no per-entry recalculation, pricing is already known from the cart
        orderEntryService.saveAll(orderEntryDtos);

        // Totals copied directly from the cart's already-computed values
        OrderDto finalOrder = orderService.applyTotals(
                orderId, cart.getOriginalPrice(), cart.getTotalPrice(), cart.getDiscount());

        InvoiceDto invoice = invoiceService.generate(orderId, cartId, paymentReference, finalOrder);
        invoice.setEntries(orderEntryService.findByOrderId(orderId));

        cartService.delete(cartId);

        return invoice;
    }
}