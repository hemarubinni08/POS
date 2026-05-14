package com.ust.pos.api.cartapi;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.CartDto;
import com.ust.pos.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartApiController extends BaseController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartDto addCart(@RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }

    @PostMapping("/getCart")
    public CartDto getCart(@RequestBody CartDto cartDto) {
        return cartService.findByIdentifier(cartDto.getIdentifier());
    }

    @PostMapping("/addToCart")
    public CartDto addToCart(@RequestBody CartDto cartDto){
        return cartService.recalculate(cartDto.getIdentifier());
    }
}
