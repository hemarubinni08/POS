package com.ust.pos.api.models;

import com.ust.pos.api.BaseController;
import com.ust.pos.dto.ModelsDto;
import com.ust.pos.dto.PaginationDto;
import com.ust.pos.models.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("modelsApiController")
@RequestMapping("/api/models")
public class ModelsController extends BaseController {

    @Autowired
    private ModelsService modelsService;

    @PostMapping("/list")
    public ResponseEntity<List<ModelsDto>> list(@RequestBody PaginationDto paginationDto) {

        Pageable pageable = getPageable(paginationDto.getPage(), paginationDto.getSizePerPage(), paginationDto.getSortDirection(), paginationDto.getSortField());

        List<ModelsDto> models = modelsService.findAll(pageable);

        return ResponseEntity.ok(models);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<?> getByIdentifier(@PathVariable String identifier) {

        ModelsDto response = modelsService.findByIdentifier(identifier);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Model not found");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ModelsDto modelsDto) {

        ModelsDto response = modelsService.save(modelsDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<?> update(@PathVariable String identifier, @RequestBody ModelsDto modelsDto) {

        modelsDto.setIdentifier(identifier);

        ModelsDto response = modelsService.update(modelsDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<?> delete(@PathVariable String identifier) {

        try {

            boolean deleted = modelsService.delete(identifier);

            if (!deleted) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to delete model");
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{identifier}/toggle-status")
    public ResponseEntity<ModelsDto> toggleStatus(@PathVariable String identifier) {

        ModelsDto response = modelsService.toggleStatus(identifier);

        return ResponseEntity.ok(response);
    }
}