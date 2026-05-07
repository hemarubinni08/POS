package com.ust.pos;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.warehouse.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE =================
    @Test
    void saveTest() {

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(null);

        Warehouse warehouse = new Warehouse();

        Mockito.when(modelMapper.map(dto, Warehouse.class))
                .thenReturn(warehouse);

        Mockito.when(warehouseRepository.save(warehouse))
                .thenReturn(warehouse);

        WarehouseDto response = warehouseService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("W1", response.getIdentifier());
    }

    @Test
    void saveTestFailure_AlreadyExists() {

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(new Warehouse());

        WarehouseDto response = warehouseService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Warehouse already exists", response.getMessage());
    }

    @Test
    void saveTestFailure_EmptyIdentifier() {

        WarehouseDto dto = new WarehouseDto();

        WarehouseDto response = warehouseService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Identifier required", response.getMessage());
    }

    // ================= FIND BY IDENTIFIER =================
    @Test
    void findByIdentifierTest() {

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("W1");

        WarehouseDto mapped = new WarehouseDto();
        mapped.setIdentifier("W1");

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(warehouse);

        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class))
                .thenReturn(mapped);

        WarehouseDto response = warehouseService.findByIdentifier("W1");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("W1", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(null);

        WarehouseDto response = warehouseService.findByIdentifier("W1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Warehouse not found", response.getMessage());
    }

    // ================= UPDATE =================
    @Test
    void updateTest() {

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Warehouse existing = new Warehouse();
        existing.setIdentifier("W1");

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(existing);

        Mockito.when(warehouseRepository.save(existing))
                .thenReturn(existing);

        WarehouseDto response = warehouseService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(null);

        WarehouseDto response = warehouseService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Warehouse not found", response.getMessage());
    }

    // ================= DELETE =================
    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(warehouseRepository)
                .deleteByIdentifier("W1");

        warehouseService.delete("W1");

        Mockito.verify(warehouseRepository)
                .deleteByIdentifier("W1");
    }

    // ================= FIND ALL (FIXED) =================
    @Test
    void findAllTest() {

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("W1");

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Mockito.when(warehouseRepository.findAll())
                .thenReturn(List.of(warehouse));

        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class))
                .thenReturn(dto);

        List<WarehouseDto> response = warehouseService.findAll();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("W1", response.get(0).getIdentifier());
    }
}