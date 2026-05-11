package com.ust.pos.api.shelf;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.ShelfDto;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("shelfApiController")
@RequestMapping("/api/shelves")
public class ShelfController extends BaseController {

    @Autowired
    private ShelfService shelfService;

    @PostMapping("/list")
    public ResponseEntity<List<ShelfDto>> list(@RequestBody PaginationDto paginationDto) {

        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());

        List<ShelfDto> shelves = shelfService.findAll(pageable);

        return ResponseEntity.ok(shelves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        ShelfDto response = shelfService.getShelf(id);

        if (response == null || !response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response != null ? response.getMessage() : "Shelf not found");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ShelfDto shelfDto) {

        ShelfDto response = shelfService.createShelf(shelfDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ShelfDto shelfDto) {

        shelfDto.setId(id);

        ShelfDto response = shelfService.updateShelf(shelfDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {

            shelfService.deleteShelf(id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/toggle-status")
    public ResponseEntity<ShelfDto> toggleStatus(@PathVariable Long id) {

        ShelfDto response = shelfService.toggleStatus(id);

        return ResponseEntity.ok(response);
    }
}