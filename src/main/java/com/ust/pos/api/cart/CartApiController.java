package com.ust.pos.api.cart;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.CartDto;
import com.ust.pos.cart.service.CartService;
import com.ust.pos.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartApiController extends BaseController {

    @Autowired
    private CartService cartService;

    @Autowired
    private NodeService nodeService;

    @PostMapping("/list")
    public List<CartDto> home(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return cartService.findAll(pageable);
    }

    @PostMapping("/add")
    public CartDto addPost(@RequestBody CartDto userDto) {
        return cartService.save(userDto);
    }

    @PostMapping("/get")
    public CartDto update(@RequestBody String identifier) {
        return cartService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public CartDto updatePost(@RequestBody CartDto userDto) {
        return cartService.update(userDto);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody String identifier) {
        try {
            cartService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
