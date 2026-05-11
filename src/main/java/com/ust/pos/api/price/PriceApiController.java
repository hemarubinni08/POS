package com.ust.pos.api.price;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceApiController extends BaseController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

    @PostMapping("/list")
    public List<PriceDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(),
                paginationDto.getSortDirection(), paginationDto.getSortField());
        return priceService.findAll(pageable);
    }

    // method to handle the form submission of price addition
    @PostMapping("/add")
    public PriceDto addPost(@RequestBody PriceDto priceDto) {
        return priceService.save(priceDto);
    }

    // method to load the price profile page
    @GetMapping("/get")
    public PriceDto update(@RequestParam long id) {
        return priceService.findById(id);
    }

    // method to handle the update of the product details
    @PostMapping("/update")
    public PriceDto updatePost(@RequestBody PriceDto priceDto) {
        return priceService.save(priceDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam long id) {
        try {
            priceService.delete(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
