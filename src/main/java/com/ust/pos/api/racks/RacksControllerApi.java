package com.ust.pos.api.racks;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.dto.RacksDto;
import com.ust.pos.racks.service.RacksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
public class RacksControllerApi extends BaseController {
    @Autowired
    RacksService racksService;

    @GetMapping("/list")
    public List<RacksDto> listCategories(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());
        return racksService.findAll(pageable);

    }

    @PostMapping("/add")
    public RacksDto saveRacks(@RequestBody RacksDto racksDto) {

        return racksService.save(racksDto);

    }


    @GetMapping("/save")
    public RacksDto showEditPage(@RequestParam Long id) {

        return racksService.findById(id);
    }

    @PostMapping("/save")
    public RacksDto saveEditedRacks(@RequestBody RacksDto racksDto) {

        return racksService.save(racksDto);

    }


    @GetMapping("/delete")
    public boolean deleteRacks(@RequestParam Long id) {

        try {
            racksService.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("/changeStatus")
    public RacksDto toggle(@RequestBody RacksDto racksDto) {
        return racksService.changeRacksStatus(racksDto.getIdentifier(), racksDto.isStatus());
    }
}