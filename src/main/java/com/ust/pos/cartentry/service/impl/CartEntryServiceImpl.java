package com.ust.pos.cartentry.service.impl;

import com.ust.pos.cart.service.CartService;
import com.ust.pos.cartentry.service.CartEntryService;
import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.model.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartEntryServiceImpl implements CartEntryService {

    @Autowired
    private CartEntryRepository cartEntryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PriceRepository priceRepository;
    @Lazy
    @Autowired
    private CartService cartService;
    @Autowired
    private StockRepository stockRepository;

    @Override
    public CartEntryDto save(CartEntryDto cartEntryDto) {
        String identifier = cartEntryDto.getCartIdentifier() + "-"
                + cartEntryDto.getProductIdentifier();

        CartEntry existing = cartEntryRepository.findByIdentifier(identifier);

        BigDecimal existingQuantity = existing != null
                ? existing.getQuantity()
                : BigDecimal.ZERO;

        BigDecimal requestedQuantity = existingQuantity.add(cartEntryDto.getQuantity());

        Stock stock = stockRepository.findByProduct(
                cartEntryDto.getProductIdentifier()
        );

        if (stock == null) {
            cartEntryDto.setSuccess(false);
            cartEntryDto.setMessage("Stock not found for this product");
            return cartEntryDto;
        }

        BigDecimal availableStock = BigDecimal.valueOf(stock.getQuantity());

        if (requestedQuantity.compareTo(availableStock) > 0) {
            cartEntryDto.setSuccess(false);
            cartEntryDto.setMessage(
                    "Only " + availableStock + " quantity available in stock"
            );
            return cartEntryDto;
        }

        CartEntry cartEntry;

        if (existing != null) {
            cartEntry = existing;
        } else {
            cartEntry = new CartEntry();
            cartEntry.setIdentifier(identifier);
            cartEntry.setCartIdentifier(cartEntryDto.getCartIdentifier());
            cartEntry.setProductIdentifier(cartEntryDto.getProductIdentifier());
        }

        Price sellingPrice = priceRepository.findByProductIdentifierAndPriceType(
                cartEntryDto.getProductIdentifier(),
                "SELLING_PRICE"
        );

        Price mrp = priceRepository.findByProductIdentifierAndPriceType(
                cartEntryDto.getProductIdentifier(),
                "MRP"
        );

        BigDecimal unitPrice = sellingPrice.getPriceAmount();
        BigDecimal mrpPrice = mrp.getPriceAmount();

        cartEntry.setQuantity(requestedQuantity);
        cartEntry.setUnitPrice(unitPrice);

        BigDecimal originalPrice = requestedQuantity.multiply(mrpPrice);
        cartEntry.setOriginalPrice(originalPrice);

        BigDecimal discountPerUnit = mrpPrice.subtract(unitPrice);
        BigDecimal totalDiscount = discountPerUnit.multiply(requestedQuantity);

        cartEntry.setDiscount(totalDiscount);
        cartEntry.setTotalPrice(unitPrice.multiply(requestedQuantity));

        cartEntryRepository.save(cartEntry);
        cartService.recalculate(cartEntry.getCartIdentifier());

        CartEntryDto response = modelMapper.map(cartEntry, CartEntryDto.class);
        response.setSuccess(true);
        return response;
    }

    public void reduceQuantity(String cartIdentifier, String productIdentifier) {
        String identifier = cartIdentifier + "-" + productIdentifier;
        CartEntry entry = cartEntryRepository.findByIdentifier(identifier);
        if (entry == null) {
            return;
        }
        BigDecimal qty = entry.getQuantity().subtract(BigDecimal.ONE);
        if (qty.compareTo(BigDecimal.ZERO) <= 0) {
            cartEntryRepository.deleteByIdentifier(identifier);
        } else {
            entry.setQuantity(qty);
            entry.setTotalPrice(entry.getUnitPrice().multiply(qty)
            );
            cartEntryRepository.save(entry);
        }
        cartService.recalculate(cartIdentifier);
    }

    @Override
    @Transactional
    public void delete(String identifier) {
        cartEntryRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<CartEntryDto> findAllEntriesForCart(String cartIdentifier) {
        List<CartEntry> cartEntryList = cartEntryRepository.findByCartIdentifier(cartIdentifier);
        Type listType = new TypeToken<List<CartEntryDto>>() {
        }.getType();
        return modelMapper.map(cartEntryList, listType);
    }


    @Override
    @Transactional
    public void deleteAllByCartIdentifier(String cart) {
        cartEntryRepository.deleteAllByCartIdentifier(cart);
    }

}
