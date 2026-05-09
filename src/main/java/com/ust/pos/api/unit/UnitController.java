package com.ust.pos.api.unit;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.UnitDto;
import com.ust.pos.unit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("unitApiController")
@RequestMapping("/api/units")
public class UnitController extends BaseController {

    @Autowired
    private UnitService unitService;

    @GetMapping("/list")
    public List<UnitDto> list(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return unitService.findAll(pageable);
    }

    @GetMapping("/{identifier}")
    public UnitDto getByIdentifier(@PathVariable String identifier) {
        return unitService.findByIdentifier(identifier);
    }

    @PostMapping("/save")
    public UnitDto save(@RequestBody UnitDto unitDto) {
        return unitService.save(unitDto);
    }

    @PostMapping("/update/{identifier}")
    public UnitDto update(@PathVariable String identifier,
                          @RequestBody UnitDto unitDto) {
        unitDto.setIdentifier(identifier);
        return unitService.update(unitDto);
    }

    @PostMapping("/delete/{identifier}")
    public boolean delete(@PathVariable String identifier) {
        try {
            unitService.delete(identifier);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/toggle/{identifier}")
    public UnitDto toggleStatus(@PathVariable String identifier) {
        return unitService.toggleStatus(identifier);
    }
}