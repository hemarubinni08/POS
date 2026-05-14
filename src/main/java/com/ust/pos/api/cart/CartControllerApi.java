package com.ust.pos.api.cart;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.CartDto;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartControllerApi extends BaseController {

    public static final String REDIRECT_ROLE_LIST = "redirect:/cart/list";

    @Autowired
    private CartService cartService;

    @PostMapping("/list")
    public List<CartDto> list(@RequestBody PaginationDto pagination) {
        Pageable pageable = getPageable(pagination.getPage(), pagination.getSizePerPage(),
                pagination.getSortDirection(), pagination.getSortfield());
        return cartService.findAll(pageable);
    }

    @PostMapping("/add")
    public CartDto addPost(@RequestBody CartDto cartDto) {
        return cartService.save(cartDto);
    }

    @GetMapping("/get")
    public CartDto updatePage(@RequestParam String identifier) {
        return cartService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public CartDto updatePost(@RequestBody CartDto cartDto) {
        return cartService.update(cartDto);
    }

    @PostMapping("/delete")
    public CartDto delete(@RequestBody CartDto cartDto) {
        CartDto response = new CartDto();
        try {
            cartService.delete(cartDto.getIdentifier());
            response.setSuccess(true);
            response.setMessage("Cart deleted successfully");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Delete failed");
        }
        return response;
    }

}