package com.ust.pos.api.brand;

import com.ust.pos.api.BaseController;
import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.WsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandRestController extends BaseController {

    public static final String REDIRECT_ROLE_LIST = "redirect:/brand/list";
    public static final String ROLE_ADD = "brand/add";
    @Autowired
    private BrandService brandService;

    @PostMapping("/list")
    public WsDto<BrandDto> home(@RequestBody PaginationDto paginationDto) {

        Pageable pageable = getPageable(paginationDto.getPage(),
                paginationDto.getSizePerPage(),
                paginationDto.getSortDirection(), paginationDto.getSortField());
        return brandService.findAll(pageable);
    }


    @PostMapping("/add")
    public BrandDto addPost(@RequestBody BrandDto userDto) {
        return brandService.save(userDto);
    }

    @PostMapping("/get")
    public BrandDto update(@RequestBody String identifier) {
        return brandService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public BrandDto updatePost(@RequestBody BrandDto userDto) {
        return brandService.update(userDto);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody String identifier) {
        try {
            brandService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
