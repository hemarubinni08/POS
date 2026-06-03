package com.ust.pos;

import com.ust.pos.cart.service.impl.CartServiceImpl;
import com.ust.pos.cartEntry.service.CartEntryService;
import com.ust.pos.dto.CartDto;
import com.ust.pos.model.Cart;
import com.ust.pos.model.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CartEntryService cartEntryService;

    @Test
    void saveSuccessTest() {
        CartDto dto = new CartDto();
        dto.setIdentifier("cart1");

        Cart cart = new Cart();
        cart.setIdentifier("cart1");

        when(cartRepository.findByIdentifier("cart1")).thenReturn(null);
        when(modelMapper.map(dto, Cart.class)).thenReturn(cart);

        CartDto result = cartService.save(dto);

        assertEquals("cart1", result.getIdentifier());
        verify(cartRepository).save(cart);
        verify(cartEntryService).recalculate("cart1");
    }

    @Test
    void saveFailureTest() {
        CartDto dto = new CartDto();
        dto.setIdentifier("cart1");

        when(cartRepository.findByIdentifier("cart1")).thenReturn(new Cart());

        CartDto result = cartService.save(dto);

        assertFalse(result.isSuccess());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void updateSuccessTest() {
        CartDto dto = new CartDto();
        dto.setIdentifier("cart1");

        Cart existing = new Cart();
        existing.setIdentifier("cart1");

        when(cartRepository.findByIdentifier("cart1")).thenReturn(existing);

        CartDto result = cartService.update(dto);

        assertEquals("cart1", result.getIdentifier());
        verify(modelMapper).map(dto, existing);
        verify(cartRepository).save(existing);
    }

    @Test
    void updateFailureTest() {
        CartDto dto = new CartDto();
        dto.setIdentifier("cart1");

        when(cartRepository.findByIdentifier("cart1")).thenReturn(null);

        CartDto result = cartService.update(dto);

        assertFalse(result.isSuccess());
        verify(cartRepository, never()).save(any());
    }

    @Test
    void deleteTest() {
        cartService.delete("cart1");
        verify(cartRepository).deleteByIdentifier("cart1");
    }

    @Test
    void findByIdentifierTest() {
        Cart cart = new Cart();
        cart.setIdentifier("cart1");

        CartDto dto = new CartDto();
        dto.setIdentifier("cart1");

        when(cartRepository.findByIdentifier("cart1")).thenReturn(cart);
        when(modelMapper.map(cart, CartDto.class)).thenReturn(dto);

        CartDto result = cartService.findByIdentifier("cart1");

        assertNotNull(result);
        assertEquals("cart1", result.getIdentifier());
    }

    @Test
    void findAllTest() {
        Pageable pageable = PageRequest.of(0, 10);

        Cart cart1 = new Cart();
        cart1.setIdentifier("cart1");

        Cart cart2 = new Cart();
        cart2.setIdentifier("cart2");

        List<Cart> carts = List.of(cart1, cart2);
        Page<Cart> page = new PageImpl<>(carts);

        CartDto dto1 = new CartDto();
        dto1.setIdentifier("cart1");

        CartDto dto2 = new CartDto();
        dto2.setIdentifier("cart2");

        when(cartRepository.findAll(pageable)).thenReturn(page);
        when(modelMapper.map(cart1, CartDto.class)).thenReturn(dto1);
        when(modelMapper.map(cart2, CartDto.class)).thenReturn(dto2);

        when(cartEntryService.findByCartId("cart1")).thenReturn(List.of());
        when(cartEntryService.findByCartId("cart2")).thenReturn(List.of());

        List<CartDto> result = cartService.findAll(pageable);

        assertEquals(2, result.size());
        assertNotNull(result.get(0).getCartEntryDtoList());
        assertNotNull(result.get(1).getCartEntryDtoList());

        verify(cartRepository).findAll(pageable);
        verify(cartEntryService).findByCartId("cart1");
        verify(cartEntryService).findByCartId("cart2");
    }
}