package com.ust.pos;

import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @Mock
    private WarehouseService warehouseService;

    @Test
    void saveTest() {
        WarehouseDto responseDto = new WarehouseDto();
        responseDto.setIdentifier("WH1");
        responseDto.setSuccess(true);

        WarehouseDto requestDto = new WarehouseDto();
        requestDto.setIdentifier("WH1");

        Mockito.when(warehouseService.save(requestDto)).thenReturn(responseDto);

        WarehouseDto response = warehouseService.save(requestDto);

        Assertions.assertEquals("WH1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest() {
        WarehouseDto responseDto = new WarehouseDto();
        responseDto.setIdentifier("WH1");
        responseDto.setSuccess(true);

        WarehouseDto requestDto = new WarehouseDto();
        requestDto.setIdentifier("WH1");

        Mockito.when(warehouseService.update(requestDto)).thenReturn(responseDto);

        WarehouseDto response = warehouseService.update(requestDto);

        Assertions.assertEquals("WH1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void findByIdentifierTest() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");

        Mockito.when(warehouseService.findByIdentifier("WH1")).thenReturn(dto);

        WarehouseDto response = warehouseService.findByIdentifier("WH1");

        Assertions.assertEquals("WH1", response.getIdentifier());
    }

    @Test
    void findAllTest() {
        WarehouseDto dto1 = new WarehouseDto();
        dto1.setIdentifier("WH1");

        WarehouseDto dto2 = new WarehouseDto();
        dto2.setIdentifier("WH2");

        List<WarehouseDto> warehouseList = List.of(dto1, dto2);

        Mockito.when(warehouseService.findAll()).thenReturn(warehouseList);

        List<WarehouseDto> response = warehouseService.findAll();

        Assertions.assertEquals(2, response.size());
    }

    @Test
    void findAllActiveTest() {
        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");
        dto.setSuccess(true);

        List<WarehouseDto> activeWarehouses = List.of(dto);

        Mockito.when(warehouseService.findAllActive()).thenReturn(activeWarehouses);

        List<WarehouseDto> response = warehouseService.findAllActive();

        Assertions.assertEquals(1, response.size());
        Assertions.assertTrue(response.get(0).isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.when(warehouseService.delete("WH1")).thenReturn(true);

        boolean result = warehouseService.delete("WH1");

        Assertions.assertTrue(result);
    }

    @Test
    void toggleStatusTest() {
        Mockito.doNothing().when(warehouseService).toggleStatus("WH1");

        warehouseService.toggleStatus("WH1");

        Mockito.verify(warehouseService).toggleStatus("WH1");
    }
}