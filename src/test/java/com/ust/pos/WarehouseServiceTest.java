package com.ust.pos;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.model.Warehouse;
import com.ust.pos.model.WarehouseRepository;
import com.ust.pos.warehouse.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Warehouse warehouse = new Warehouse();

        Mockito.when(warehouseRepository.findByIdentifier("W1")).thenReturn(null);
        Mockito.when(modelMapper.map(dto, Warehouse.class)).thenReturn(warehouse);
        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class)).thenReturn(dto);

        WarehouseDto response = warehouseService.save(dto);

        assertTrue(response.isSuccess());
        Mockito.verify(warehouseRepository).save(warehouse);
    }

    @Test
    void saveDuplicateTest() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(new Warehouse());

        WarehouseDto response = warehouseService.save(dto);

        assertFalse(response.isSuccess());
    }

    @Test
    void updateTest() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Warehouse warehouse = new Warehouse();
        warehouse.setStatus(true);

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(warehouse);

        // FIX: void mapping
        Mockito.doNothing().when(modelMapper).map(dto, warehouse);

        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class))
                .thenReturn(dto);

        WarehouseDto response = warehouseService.update(dto);

        assertTrue(response.isSuccess());
        Mockito.verify(warehouseRepository).save(warehouse);
    }

    @Test
    void updateNotFoundTest() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("W1");

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(null);

        WarehouseDto response = warehouseService.update(dto);

        assertFalse(response.isSuccess());
    }

    @Test
    void findByIdentifierTest() {
        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("W1");

        WarehouseDto dto = new WarehouseDto();

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(warehouse);
        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class))
                .thenReturn(dto);

        WarehouseDto response = warehouseService.findByIdentifier("W1");

        assertNotNull(response);
    }

    @Test
    void findAllTest() {
        Warehouse warehouse = new Warehouse();
        List<Warehouse> list = List.of(warehouse);

        WarehouseDto dto = new WarehouseDto();
        List<WarehouseDto> dtoList = List.of(dto);

        Mockito.when(warehouseRepository.findAll()).thenReturn(list);

        // FIX: match correct args
        Mockito.when(modelMapper.map(any(), any(Type.class)))
                .thenReturn(dtoList);

        List<WarehouseDto> response = warehouseService.findAll();

        assertEquals(1, response.size());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(warehouseRepository)
                .deleteByIdentifier("W1");

        boolean result = warehouseService.delete("W1");

        assertTrue(result);
        Mockito.verify(warehouseRepository).deleteByIdentifier("W1");
    }

    @Test
    void toggleStatusTest() {
        Warehouse warehouse = new Warehouse();
        warehouse.setStatus(true);

        Mockito.when(warehouseRepository.findByIdentifier("W1"))
                .thenReturn(warehouse);

        warehouseService.toggleStatus("W1");

        assertFalse(warehouse.isStatus());
        Mockito.verify(warehouseRepository).save(warehouse);
    }

    @Test
    void findIfTrueTest() {
        Warehouse warehouse = new Warehouse();
        List<Warehouse> list = List.of(warehouse);

        WarehouseDto dto = new WarehouseDto();
        List<WarehouseDto> dtoList = List.of(dto);

        Mockito.when(warehouseRepository.findByStatusIsTrue())
                .thenReturn(list);

        // FIX: correct mapping
        Mockito.when(modelMapper.map(any(), any(Type.class)))
                .thenReturn(dtoList);

        List<WarehouseDto> response = warehouseService.findIfTrue();

        assertEquals(1, response.size());
    }
}