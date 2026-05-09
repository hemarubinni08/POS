package com.ust.pos.api.rack;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.RackDto;
import com.ust.pos.dto.ShelfDto;
import com.ust.pos.rack.service.RackService;
import com.ust.pos.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("rackApiController")
@RequestMapping("/api/racks")
public class RackController extends BaseController {

    @Autowired
    private RackService rackService;

    @Autowired
    private ShelfService shelfService;

    @GetMapping("/list")
    public List<RackDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return rackService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public RackDto getById(@PathVariable Long id) {
        return rackService.getRack(id);
    }

    @PostMapping("/save")
    public RackDto save(@RequestBody RackDto rackDto) {
        return rackService.createRack(rackDto);
    }

    @PostMapping("/update/{id}")
    public RackDto update(@PathVariable Long id,
                          @RequestBody RackDto rackDto) {
        rackDto.setId(id);
        return rackService.updateRack(rackDto);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        try {
            rackService.deleteRack(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/shelves")
    public List<ShelfDto> getShelves() {
        return shelfService.findAll(null);
    }
}