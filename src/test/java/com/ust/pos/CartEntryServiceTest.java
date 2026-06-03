package com.ust.pos;

import com.ust.pos.cartEntry.service.impl.CartEntryServiceImpl;
import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Cart;
import com.ust.pos.model.CartEntry;
import com.ust.pos.model.CartEntryRepository;
import com.ust.pos.model.CartRepository;
import com.ust.pos.price.service.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartEntryServiceTest {

    @InjectMocks
    private CartEntryServiceImpl cartEntryService;

    @Mock
    private CartEntryRepository cartEntryRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private PriceService priceService;

    @Mock(lenient = true)
    private ModelMapper modelMapper;

    @Test
    void saveNewEntryTest() {
        CartEntryDto dto = new CartEntryDto();
        dto.setProduct("Laptop");
        dto.setCartId("cart1");
        dto.setQuantity(BigDecimal.valueOf(2));

        PriceDto selling = new PriceDto();
        selling.setPriceAmount(BigDecimal.valueOf(100));

        PriceDto mrp = new PriceDto();
        mrp.setPriceAmount(BigDecimal.valueOf(120));

        when(priceService.findByProductAndPriceType("Laptop", "Selling price"))
                .thenReturn(selling);
        when(priceService.findByProductAndPriceType("Laptop", "MRP"))
                .thenReturn(mrp);

        when(cartEntryRepository.findByIdentifier("cart1_Laptop"))
                .thenReturn(null);

        CartEntry entity = new CartEntry();
        when(modelMapper.map(any(CartEntryDto.class), eq(CartEntry.class)))
                .thenReturn(entity);

        when(cartEntryRepository.findAllByCartId("cart1"))
                .thenReturn(List.of());

        when(cartRepository.findByIdentifier("cart1"))
                .thenReturn(new Cart());

        CartEntryDto result = cartEntryService.save(dto);

        assertNotNull(result);
        assertEquals("cart1_Laptop", result.getIdentifier());
        verify(cartEntryRepository).save(any(CartEntry.class));
    }

    @Test
    void saveExistingEntryTest() {

        CartEntryDto dto = new CartEntryDto();
        dto.setProduct("Laptop");
        dto.setCartId("cart1");
        dto.setQuantity(BigDecimal.valueOf(2));

        CartEntry existing = new CartEntry();
        existing.setQuantity(BigDecimal.valueOf(3));
        existing.setIdentifier("cart1_Laptop");

        existing.setTotalPrice(BigDecimal.valueOf(300));
        existing.setDiscount(BigDecimal.valueOf(30));
        existing.setTotalOriginalPrice(BigDecimal.valueOf(330));

        PriceDto selling = new PriceDto();
        selling.setPriceAmount(BigDecimal.valueOf(100));

        PriceDto mrp = new PriceDto();
        mrp.setPriceAmount(BigDecimal.valueOf(120));

        when(cartEntryRepository.findByIdentifier("cart1_Laptop"))
                .thenReturn(existing);

        when(priceService.findByProductAndPriceType("Laptop", "Selling price"))
                .thenReturn(selling);

        when(priceService.findByProductAndPriceType("Laptop", "MRP"))
                .thenReturn(mrp);

        doAnswer(invocation -> {
            CartEntryDto source = invocation.getArgument(0);
            CartEntry target = invocation.getArgument(1);
            target.setQuantity(source.getQuantity());
            target.setTotalPrice(source.getTotalPrice());
            target.setDiscount(source.getDiscount());
            target.setTotalOriginalPrice(source.getTotalOriginalPrice());
            return null;
        }).when(modelMapper).map(any(CartEntryDto.class), any(CartEntry.class));

        when(cartEntryRepository.findAllByCartId("cart1"))
                .thenReturn(List.of(existing));

        when(cartRepository.findByIdentifier("cart1"))
                .thenReturn(new Cart());

        CartEntryDto result = cartEntryService.save(dto);

        assertEquals(BigDecimal.valueOf(5), result.getQuantity());
        verify(cartEntryRepository).save(existing);
    }

    @Test
    void deleteTest() {
        when(cartEntryRepository.findAllByCartId("cart1")).thenReturn(List.of());
        when(cartRepository.findByIdentifier("cart1")).thenReturn(new Cart());

        cartEntryService.delete("cart1", "Laptop");

        verify(cartEntryRepository).deleteByCartIdAndProduct("cart1", "Laptop");
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void findByIdentifierTest() {
        CartEntry entry = new CartEntry();
        entry.setIdentifier("cart1_Laptop");

        CartEntryDto dto = new CartEntryDto();
        dto.setIdentifier("cart1_Laptop");

        when(cartEntryRepository.findByIdentifier("cart1_Laptop")).thenReturn(entry);
        when(modelMapper.map(entry, CartEntryDto.class)).thenReturn(dto);

        CartEntryDto result = cartEntryService.findByIdentifier("cart1_Laptop");

        assertNotNull(result);
        assertEquals("cart1_Laptop", result.getIdentifier());
    }

    @Test
    void findAllTest() {
        Pageable pageable = PageRequest.of(0, 10);

        List<CartEntry> entities = List.of(new CartEntry(), new CartEntry());
        Page<CartEntry> page = new PageImpl<>(entities);

        List<CartEntryDto> dtoList = List.of(new CartEntryDto(), new CartEntryDto());

        when(cartEntryRepository.findAll(pageable)).thenReturn(page);
        when(modelMapper.map(eq(entities), any(Type.class))).thenReturn(dtoList);

        List<CartEntryDto> result = cartEntryService.findAll(pageable);

        assertEquals(2, result.size());
    }

    @Test
    void getSellingPriceTest() {
        PriceDto dto = new PriceDto();
        dto.setPriceAmount(BigDecimal.valueOf(100));

        when(priceService.findByProductAndPriceType("Laptop", "Selling price"))
                .thenReturn(dto);

        BigDecimal result = cartEntryService.getSellingPriceAmount("Laptop");

        assertEquals(BigDecimal.valueOf(100), result);
    }

    @Test
    void getDiscountTest() {
        PriceDto mrp = new PriceDto();
        mrp.setPriceAmount(BigDecimal.valueOf(120));

        PriceDto selling = new PriceDto();
        selling.setPriceAmount(BigDecimal.valueOf(100));

        when(priceService.findByProductAndPriceType("Laptop", "MRP"))
                .thenReturn(mrp);

        when(priceService.findByProductAndPriceType("Laptop", "Selling price"))
                .thenReturn(selling);

        BigDecimal result = cartEntryService.getDiscountPriceAmount("Laptop", BigDecimal.valueOf(2));

        assertEquals(BigDecimal.valueOf(40), result);
    }

    @Test
    void getTotalPriceTest() {
        PriceDto selling = new PriceDto();
        selling.setPriceAmount(BigDecimal.valueOf(100));

        when(priceService.findByProductAndPriceType("Laptop", "Selling price"))
                .thenReturn(selling);

        BigDecimal result = cartEntryService.getTotalPrice("Laptop", BigDecimal.valueOf(2));

        assertEquals(BigDecimal.valueOf(200), result);
    }

    @Test
    void recalculateTest() {
        CartEntry e1 = new CartEntry();
        e1.setTotalPrice(BigDecimal.valueOf(200));
        e1.setDiscount(BigDecimal.valueOf(20));
        e1.setTotalOriginalPrice(BigDecimal.valueOf(220));

        CartEntry e2 = new CartEntry();
        e2.setTotalPrice(BigDecimal.valueOf(100));
        e2.setDiscount(BigDecimal.valueOf(10));
        e2.setTotalOriginalPrice(BigDecimal.valueOf(110));

        when(cartEntryRepository.findAllByCartId("cart1")).thenReturn(List.of(e1, e2));

        Cart cart = new Cart();
        when(cartRepository.findByIdentifier("cart1")).thenReturn(cart);

        cartEntryService.recalculate("cart1");

        assertEquals(BigDecimal.valueOf(300), cart.getTotalPrice());
        assertEquals(BigDecimal.valueOf(30), cart.getDiscount());
        assertEquals(BigDecimal.valueOf(330), cart.getTotalOriginalPrice());
    }

    @Test
    void findByCartIdTest() {
        List<CartEntry> entries = List.of(new CartEntry(), new CartEntry());
        List<CartEntryDto> dtoList = List.of(new CartEntryDto(), new CartEntryDto());

        when(cartEntryRepository.findAllByCartId("cart1")).thenReturn(entries);
        when(modelMapper.map(eq(entries), any(Type.class))).thenReturn(dtoList);

        List<CartEntryDto> result = cartEntryService.findByCartId("cart1");

        assertEquals(2, result.size());
    }

    @Test
    void deleteAllByCartIdTest() {
        when(cartEntryRepository.findAllByCartId("cart1")).thenReturn(List.of());
        when(cartRepository.findByIdentifier("cart1")).thenReturn(new Cart());

        cartEntryService.deleteAllByCartId("cart1");

        verify(cartEntryRepository).deleteAllByCartId("cart1");
    }
}