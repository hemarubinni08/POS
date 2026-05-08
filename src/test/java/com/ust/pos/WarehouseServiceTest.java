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
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ModelMapper modelMapper;

    // ---------------- SAVE ----------------

    @Test
    void saveTest_Success() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");

        Warehouse entity = new Warehouse();

        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(dto, Warehouse.class))
                .thenReturn(entity);
        Mockito.when(warehouseRepository.save(entity))
                .thenReturn(entity);

        WarehouseDto response = warehouseService.save(dto);

        Assertions.assertEquals("WH1", response.getIdentifier());
        Mockito.verify(warehouseRepository).save(entity);
    }

    @Test
    void saveTest_Failure_WhenAlreadyExists() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");

        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(new Warehouse());

        WarehouseDto response = warehouseService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());

        Mockito.verify(warehouseRepository, Mockito.never())
                .save(Mockito.any());
    }

    // ---------------- UPDATE ----------------

    @Test
    void updateTest_Success() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");

        Warehouse existing = new Warehouse();
        existing.setIdentifier("WH1");

        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(existing);

        Mockito.doNothing()
                .when(modelMapper).map(dto, existing);

        Mockito.when(warehouseRepository.save(existing))
                .thenReturn(existing);

        WarehouseDto response = warehouseService.update(dto);

        Assertions.assertEquals("WH1", response.getIdentifier());
        Mockito.verify(warehouseRepository).save(existing);
    }

    @Test
    void updateTest_Failure_WhenNotFound() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");

        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(null);

        WarehouseDto response = warehouseService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());

        Mockito.verify(warehouseRepository, Mockito.never())
                .save(Mockito.any());
    }

    // ---------------- FIND BY IDENTIFIER ----------------

    @Test
    void findByIdentifierTest() {
        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("WH1");

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");

        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(warehouse);
        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class))
                .thenReturn(dto);

        WarehouseDto response = warehouseService.findByIdentifier("WH1");

        Assertions.assertEquals("WH1", response.getIdentifier());
    }

    // ---------------- FIND ALL ----------------

    @Test
    void findAllTest() {
        List<Warehouse> entities = List.of(new Warehouse());
        List<WarehouseDto> dtos = List.of(new WarehouseDto());

        Type listType = new TypeToken<List<WarehouseDto>>() {}.getType();

        Mockito.when(warehouseRepository.findAll())
                .thenReturn(entities);
        Mockito.when(modelMapper.map(entities, listType))
                .thenReturn(dtos);

        List<WarehouseDto> response = warehouseService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    // ---------------- DELETE ----------------

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(warehouseRepository)
                .deleteByIdentifier("WH1");

        warehouseService.delete("WH1");

        Mockito.verify(warehouseRepository)
                .deleteByIdentifier("WH1");
    }
}