package com.ust.pos.api.brand;

import com.ust.pos.api.BaseController;
import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.CustomerDto;
import com.ust.pos.dto.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("brandApiController")
@RequestMapping("/api/brands")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/list")
    public List<BrandDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return brandService.findAll(pageable);
    }

    @GetMapping("/{identifier}")
    public BrandDto getByIdentifier(@PathVariable String identifier) {
        return brandService.findByIdentifier(identifier);
    }

    @PostMapping("/save")
    public BrandDto save(@RequestBody BrandDto brandDto) {
        return brandService.save(brandDto);
    }

    @PostMapping("/update/{identifier}")
    public BrandDto update(@PathVariable String identifier,
                           @RequestBody BrandDto brandDto) {
        brandDto.setIdentifier(identifier);
        return brandService.update(brandDto);
    }

    @PostMapping("/delete/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            brandService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{identifier}")
    public BrandDto toggleStatus(@PathVariable String identifier) {
        return brandService.toggleStatus(identifier);
    }

    @GetMapping("/active")
    public List<BrandDto> getActiveBrands() {
        return brandService.findIfTrue();
    }
}