package com.ust.pos.api.price;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.price.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceRestController extends BaseController {

    public static final String REDIRECT_ROLE_LIST = "redirect:/price/list";
    public static final String ROLE_ADD = "price/add";
    @Autowired
    private PriceService priceService;

    @PostMapping("/list")
    public WsDto<PriceDto> home(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(),
                paginationDto.getSizePerPage(),
                paginationDto.getSortDirection(), paginationDto.getSortField());
        return priceService.findAll(pageable);
    }

    @PostMapping("/add")
    public PriceDto addPost(@RequestBody PriceDto userDto) {
        return priceService.save(userDto);
    }

    @PostMapping("/get")
    public PriceDto update(@RequestBody String identifier) {
        return priceService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public PriceDto updatePost(@RequestBody PriceDto userDto) {
        return priceService.update(userDto);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody String identifier) {
        try {
            priceService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
