package com.ust.pos.api.price;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.price.service.PriceService;
import com.ust.pos.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("priceApiController")
@RequestMapping("/api/prices")
public class PriceController extends BaseController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

    @PostMapping("/list")
    public ResponseEntity<List<PriceDto>> list(@RequestBody PaginationDto paginationDto) {

        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());

        List<PriceDto> prices = priceService.findAll(pageable);

        return ResponseEntity.ok(prices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        PriceDto response = priceService.getPriceById(id);

        if (response == null || !response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response != null ? response.getMessage() : "Price not found");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PriceDto priceDto) {

        try {

            PriceDto response = priceService.createPrice(priceDto);

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PriceDto priceDto) {

        try {

            priceDto.setId(id);

            PriceDto response = priceService.updatePrice(priceDto);

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {

            priceService.deletePrice(id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        List<ProductDto> products = productService.findAll(null);

        return ResponseEntity.ok(products);
    }
}