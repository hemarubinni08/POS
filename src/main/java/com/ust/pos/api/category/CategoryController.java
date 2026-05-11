package com.ust.pos.api.category;

import com.ust.pos.api.BaseController;
import com.ust.pos.category.service.CategoryService;
import com.ust.pos.dto.CategoryDto;
import com.ust.pos.dto.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("categoryApiController")
@RequestMapping("/api/categories")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<CategoryDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{identifier}")
    public CategoryDto getByIdentifier(@PathVariable String identifier) {
        return categoryService.findByIdentifier(identifier);
    }

    @PostMapping("/save")
    public CategoryDto save(@RequestBody CategoryDto dto) {
        return categoryService.save(dto);
    }

    @PostMapping("/update/{identifier}")
    public CategoryDto update(@PathVariable String identifier, @RequestBody CategoryDto dto) {
        dto.setIdentifier(identifier);
        return categoryService.update(dto);
    }

    @PostMapping("/delete/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            return categoryService.delete(identifier);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{identifier}")
    public CategoryDto toggleStatus(@PathVariable String identifier) {
        return categoryService.toggleStatus(identifier);
    }

    @GetMapping("/active")
    public List<CategoryDto> activeCategories() {
        return categoryService.findIfTrue();
    }

    @GetMapping("/sub-categories")
    public List<CategoryDto> subCategories() {
        return categoryService.findSuperCategories();
    }
}