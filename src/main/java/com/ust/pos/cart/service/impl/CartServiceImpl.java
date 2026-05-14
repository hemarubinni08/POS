package com.ust.pos.cart.service.impl;

import com.ust.pos.api.BaseController;
import com.ust.pos.cart.service.CartService;
import com.ust.pos.cartEntry.service.CartEntryService;
import com.ust.pos.dto.CartDto;
import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.model.Cart;
import com.ust.pos.model.CartEntry;
import com.ust.pos.model.CartEntryRepository;
import com.ust.pos.model.CartRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartEntryService cartEntryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CartDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<CartDto>>() {}.getType();
        Page<Cart> cartPage =cartRepository.findAll(pageable);
        return modelMapper.map(cartPage.getContent(), listType);
    }

    @Override
    public CartDto save(CartDto cartDto) {
        List<CartEntryDto> cartEntryDtoList = cartEntryService.findByCartId(cartDto.getIdentifier());
        BigDecimal originalPrice=BigDecimal.ZERO;
        BigDecimal totalPrice=BigDecimal.ZERO;
        BigDecimal discout=BigDecimal.ZERO;

        for (CartEntryDto cartEntry:cartEntryDtoList){
            originalPrice=originalPrice.add(cartEntry.getOriginalPrice());
            totalPrice=totalPrice.add(cartEntry.getTotalPrice());
            discout=discout.add(cartEntry.getDiscount());
        }
        Cart cart=cartRepository.findByIdentifier(cartDto.getIdentifier());
        if(cart==null){
            cart=new Cart();
            cart.setIdentifier(cartDto.getIdentifier());
        }
        cart.setOriginalPrice(originalPrice);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(discout);
        cart.setCoupon(cartDto.getCoupon());
        cartRepository.save(cart);
        return modelMapper.map(cart,CartDto.class);
    }

    @Override
    public CartDto update(CartDto cartDto) {
        return null;
    }

    @Override
    public void delete(String identifier) {
        cartRepository.deleteByIdentifier(identifier);
    }

    @Override
    public CartDto findByIdentifier(String identifier) {
        Cart cart = cartRepository.findByIdentifier(identifier);
        if (cart == null) {
            return null;
        }
        return modelMapper.map(cart, CartDto.class);
    }
}