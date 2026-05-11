package com.ust.pos.api.brand;

import com.ust.pos.api.BaseController;
import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandControllerApi extends BaseController {

    @Autowired
    BrandService brandService;

    @GetMapping("/list")
    public List<BrandDto> listCategories(@RequestBody PaginationDto paginationDto) {

        Pageable pageable = getPageable(paginationDto.getPage(),paginationDto.getSizePerPage(),paginationDto.getSortDirection(),paginationDto.getSortField());
        return brandService.findAll(pageable);

    }

    @PostMapping("/add")
    public BrandDto saveBrand(@RequestBody BrandDto brandDto) {

        return brandService.save(brandDto);

    }


    @GetMapping("/save")
    public BrandDto showEditPage(@RequestParam String identifier) {

        return brandService.findByIdentifier(identifier);
    }

    @PostMapping("/save")
    public BrandDto saveEditedBrand(@RequestBody BrandDto brandDto) {

        return brandService.save(brandDto);
    }


    @GetMapping("/delete")
    public boolean deleteBrand(@RequestParam Long id) {
        try {
            brandService.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/changeStatus")
    public BrandDto toggle(@RequestBody BrandDto brandDto) {

        return brandService.changeBrandStatus(brandDto.getIdentifier(), brandDto.isStatus());

    }
}