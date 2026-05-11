package com.ust.pos.api.shelf;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("shelfApiController")
@RequestMapping("/api/shelves")
public class ShelfController extends BaseController {

    @Autowired
    private ShelfService shelfService;

    @PostMapping("/list")
    public List<ShelfDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return shelfService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ShelfDto getById(@PathVariable Long id) {
        return shelfService.getShelf(id);
    }

    @PostMapping("/save")
    public ShelfDto save(@RequestBody ShelfDto shelfDto) {
        return shelfService.createShelf(shelfDto);
    }

    @PostMapping("/update/{id}")
    public ShelfDto update(@PathVariable Long id, @RequestBody ShelfDto shelfDto) {
        shelfDto.setId(id);
        return shelfService.updateShelf(shelfDto);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            shelfService.deleteShelf(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{id}")
    public ShelfDto toggleStatus(@PathVariable Long id) {
        return shelfService.toggleStatus(id);
    }
}