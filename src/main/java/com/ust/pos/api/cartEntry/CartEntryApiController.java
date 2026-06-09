package com.ust.pos.api.cartEntry;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.cartEntry.service.CartEntryService;
import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartEntry")
public class CartEntryApiController extends BaseController {

    @Autowired
    private CartEntryService cartEntryService;

    @Autowired
    private NodeService nodeService;

    @PostMapping("/list")
    public PaginationResponseDto<CartEntryDto> home(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return cartEntryService.findAll(pageable);
    }

    @PostMapping("/add")
    public CartEntryDto addPost(@RequestBody CartEntryDto userDto) {
        return cartEntryService.save(userDto);
    }

    @PostMapping("/get")
    public CartEntryDto update(@RequestBody String identifier) {
        return cartEntryService.findByIdentifier(identifier);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody String cartId, @RequestParam String product) {
        try {
            cartEntryService.delete(cartId,product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/clearCart")
    public boolean deleteAll(@RequestBody String cartId){
        try {
            cartEntryService.deleteAllByCartId(cartId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
