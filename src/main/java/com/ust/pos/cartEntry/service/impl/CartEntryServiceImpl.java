package com.ust.pos.cartEntry.service.impl;

import com.ust.pos.cartEntry.service.CartEntryService;
import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.CartEntry;
import com.ust.pos.model.CartEntryRepository;
import com.ust.pos.price.service.impl.PriceServiceImpl;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartEntryServiceImpl implements CartEntryService {

    @Autowired
    private CartEntryRepository cartEntryRepository;

    @Autowired
    private PriceServiceImpl priceService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartEntryDto save(CartEntryDto cartEntryDto) {

        String cartId = cartEntryDto.getCartId();
        String productId = cartEntryDto.getProductId();
        String identifier = cartId + "_" + productId;
        if (cartEntryDto.getQuantity() == null) {
            cartEntryDto.setSuccess(false);
            cartEntryDto.setMessage("Quantity is required");
            return cartEntryDto;
        }
        CartEntry cartEntry = cartEntryRepository.findByIdentifier(identifier);
        BigDecimal quantity = cartEntryDto.getQuantity();

        if (cartEntry == null) {
            cartEntry = new CartEntry();
            cartEntry.setIdentifier(cartId+"_"+productId);
            cartEntry.setCartId(cartId);
            cartEntry.setProductId(productId);
            cartEntry.setQuantity(BigDecimal.ZERO);
        }

        BigDecimal updatedQty = cartEntry.getQuantity().add(quantity);
        cartEntry.setQuantity(updatedQty);

        PriceDto mrpDto = priceService.findByIdentifier(productId + "_MRP");
        PriceDto sellingPriceDto = priceService.findByIdentifier(productId + "_Selling_Price");

        if (mrpDto == null || sellingPriceDto == null ||
                mrpDto.getValue() == null || sellingPriceDto.getValue() == null) {
            cartEntryDto.setSuccess(false);
            cartEntryDto.setMessage("Price not configured for product: " + productId);
            return cartEntryDto;
        }

        BigDecimal mrp = mrpDto.getValue();
        BigDecimal sellingPrice = sellingPriceDto.getValue();

        cartEntry.setMrp(mrp);
        cartEntry.setSellingPrice(sellingPrice);

        BigDecimal originalPrice = updatedQty.multiply(mrp);
        BigDecimal totalPrice = updatedQty.multiply(sellingPrice);
        BigDecimal discountPerUnit = mrp.subtract(sellingPrice);
        BigDecimal totalDiscount = discountPerUnit.multiply(updatedQty);

        cartEntry.setOriginalPrice(originalPrice);
        cartEntry.setTotalPrice(totalPrice);
        cartEntry.setDiscount(totalDiscount);

        cartEntryRepository.save(cartEntry);

        CartEntryDto dto = modelMapper.map(cartEntry, CartEntryDto.class);
        dto.setSuccess(true);
        dto.setMessage("Cart entry saved successfully");

        return dto;
    }

    @Override
    public CartEntryDto update(CartEntryDto cartEntryDto) {

        CartEntry cartEntry =
                cartEntryRepository.findByIdentifier(cartEntryDto.getIdentifier());

        if (cartEntry == null) {
            cartEntryDto.setSuccess(false);
            cartEntryDto.setMessage("Cart Entry not found");
            return cartEntryDto;
        }

        if (cartEntryDto.getQuantity() == null) {
            cartEntryDto.setSuccess(false);
            cartEntryDto.setMessage("Quantity is required");
            return cartEntryDto;
        }

        cartEntry.setQuantity(cartEntryDto.getQuantity());

        BigDecimal mrp = cartEntry.getMrp();
        BigDecimal sellingPrice = cartEntry.getSellingPrice();
        BigDecimal qty = cartEntry.getQuantity();

        if (mrp != null && sellingPrice != null) {
            cartEntry.setOriginalPrice(qty.multiply(mrp));
            cartEntry.setTotalPrice(qty.multiply(sellingPrice));
            cartEntry.setDiscount(mrp.subtract(sellingPrice).multiply(qty));
        }

        cartEntryRepository.save(cartEntry);

        CartEntryDto dto = modelMapper.map(cartEntry, CartEntryDto.class);
        dto.setSuccess(true);
        dto.setMessage("Cart entry updated successfully");

        return dto;
    }

    @Override
    public CartEntryDto findByIdentifier(String identifier) {
        CartEntry cartEntry = cartEntryRepository.findByIdentifier(identifier);

        if (cartEntry == null) {
            CartEntryDto dto = new CartEntryDto();
            dto.setSuccess(false);
            dto.setMessage("Cart entry not found");
            return dto;
        }

        CartEntryDto dto = modelMapper.map(cartEntry, CartEntryDto.class);
        dto.setSuccess(true);
        return dto;
    }

    @Override
    public List<CartEntryDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<CartEntryDto>>() {
        }.getType();
        Page<CartEntry> page = cartEntryRepository.findAll(pageable);
        return modelMapper.map(page.getContent(), listType);
    }

    @Override
    public List<CartEntryDto> findByCartId(String cartId) {
        Type listType = new TypeToken<List<CartEntryDto>>() {
        }.getType();
        return modelMapper.map(cartEntryRepository.findByCartId(cartId), listType);
    }

    @Override
    public void delete(String identifier) {
        cartEntryRepository.deleteByIdentifier(identifier);
    }
}