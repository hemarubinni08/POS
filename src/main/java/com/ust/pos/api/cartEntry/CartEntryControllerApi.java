package com.ust.pos.api.cartEntry;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.CartEntryDto;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.cartEntry.service.CartEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartEntry")
public class CartEntryControllerApi extends BaseController {

    public static final String REDIRECT_ROLE_LIST = "redirect:/cartEntry/list";

    @Autowired
    private CartEntryService cartEntryService;

    @PostMapping("/list")
    public List<CartEntryDto> list(@RequestBody PaginationDto pagination) {
        Pageable pageable = getPageable(pagination.getPage(), pagination.getSizePerPage(),
                pagination.getSortDirection(), pagination.getSortfield());
        return cartEntryService.findAll(pageable);
    }

    @PostMapping("/add")
    public CartEntryDto addPost(@RequestBody CartEntryDto cartEntryDto) {
        return cartEntryService.save(cartEntryDto);
    }

    @GetMapping("/get")
    public CartEntryDto updatePage(@RequestParam String identifier) {
        return cartEntryService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public CartEntryDto updatePost(@RequestBody CartEntryDto cartEntryDto) {
        return cartEntryService.update(cartEntryDto);
    }

    @PostMapping("/delete")
    public CartEntryDto delete(@RequestBody CartEntryDto cartEntryDto) {
        CartEntryDto response = new CartEntryDto();
        try {
            cartEntryService.delete(cartEntryDto.getIdentifier());
            response.setSuccess(true);
            response.setMessage("CartEntry deleted successfully");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Delete failed");
        }
        return response;
    }

}