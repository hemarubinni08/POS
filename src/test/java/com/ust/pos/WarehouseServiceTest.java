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
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @InjectMocks
    private WarehouseServiceImpl warehouseService;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("WH1");
        Mockito.when(warehouseRepository.findByIdentifier("WH1")).thenReturn(null);
        Warehouse warehouse = new Warehouse();
        Mockito.when(modelMapper.map(warehouseDto, Warehouse.class)).thenReturn(warehouse);
        Mockito.when(warehouseRepository.save(warehouse)).thenReturn(warehouse);
        WarehouseDto response = warehouseService.save(warehouseDto);
        Assertions.assertEquals("WH1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Warehouse created successfully", response.getMessage());
    }

    @Test
    void saveTestFailure() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("WH1");
        Warehouse existingWarehouse = new Warehouse();
        Mockito.when(warehouseRepository.findByIdentifier("WH1")).thenReturn(existingWarehouse);
        WarehouseDto response = warehouseService.save(warehouseDto);
        Assertions.assertEquals("WH1", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void findByIdentifierTest() {
        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("WH1");
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("WH1");
        Mockito.when(warehouseRepository.findByIdentifier("WH1")).thenReturn(warehouse);
        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class)).thenReturn(warehouseDto);
        WarehouseDto response = warehouseService.findByIdentifier("WH1");
        Assertions.assertEquals("WH1", response.getIdentifier());
    }

    @Test
    void updateTest() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("WH1");
        Warehouse existingWarehouse = new Warehouse();
        existingWarehouse.setIdentifier("WH1");
        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(existingWarehouse);
        Mockito.when(warehouseRepository.save(existingWarehouse))
                .thenReturn(existingWarehouse);
        WarehouseDto response = warehouseService.update(warehouseDto);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Warehouse updated successfully", response.getMessage());
    }

    @Test
    void updateTestFailure() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("WH1");
        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(null);
        WarehouseDto response = warehouseService.update(warehouseDto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(warehouseRepository).deleteByIdentifier("WH1");
        boolean result = warehouseService.delete("WH1");
        Mockito.verify(warehouseRepository).deleteByIdentifier("WH1");
        Assertions.assertTrue(result);
    }

    @Test
    void findAllTest() {
        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("WH1");
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("WH1");
        List<Warehouse> warehouses = List.of(warehouse);
        List<WarehouseDto> warehouseDtos = List.of(warehouseDto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Warehouse> warehousePage =
                new PageImpl<>(warehouses, pageable, warehouses.size());
        Mockito.when(warehouseRepository.findAll(pageable))
                .thenReturn(warehousePage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(warehouses),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(warehouseDtos);
        List<WarehouseDto> response = warehouseService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("WH1", response.get(0).getIdentifier());
    }
}