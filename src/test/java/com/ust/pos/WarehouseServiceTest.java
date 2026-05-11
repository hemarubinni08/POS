package com.ust.pos;


import com.ust.pos.dto.WarehouseDto;
import com.ust.pos.modell.Warehouse;
import com.ust.pos.modell.WarehouseRepository;
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
        warehouseDto.setIdentifier("Admin");

        Mockito.when(warehouseRepository.findByIdentifier("Admin")).thenReturn(null);
        Warehouse warehouse = new Warehouse();
        Mockito.when(modelMapper.map(warehouseDto, Warehouse.class)).thenReturn(warehouse);
        Mockito.when(warehouseRepository.save(warehouse)).thenReturn(warehouse);
        WarehouseDto response = warehouseService.save(warehouseDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNull(response.getMessage());
        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    void saveTestFailure() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("Admin");
        Warehouse warehouse = new Warehouse();

        Mockito.when(warehouseRepository.findByIdentifier("Admin")).thenReturn(warehouse);
        WarehouseDto response = warehouseService.save(warehouseDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage(), "Message cannot be null");

        Assertions.assertFalse(response.isSuccess());

    }

    @Test
    void findByIdentifierTest() {
        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("Admin");

        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("Admin");

        Mockito.when(warehouseRepository.findByIdentifier("Admin")).thenReturn(warehouse);
        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class)).thenReturn(warehouseDto);

        WarehouseDto response = warehouseService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void updateTest() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("Admin");

        Warehouse existingRole = new Warehouse();
        existingRole.setIdentifier("Admin");

        Mockito.when(warehouseRepository.findByIdentifier("Admin"))
                .thenReturn(existingRole);
        Mockito.when(warehouseRepository.save(existingRole))
                .thenReturn(existingRole);

        WarehouseDto response = warehouseService.update(warehouseDto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("Admin");

        Mockito.when(warehouseRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        WarehouseDto response = warehouseService.update(warehouseDto);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {

        Mockito.doNothing().when(warehouseRepository)
                .deleteByIdentifier("Admin");

        warehouseService.delete("Admin");

        Mockito.verify(warehouseRepository).deleteByIdentifier("Admin");
    }

    @Test
    void findAllTest() {
        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("Admin");

        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setIdentifier("Admin");

        List<Warehouse> warehouses = List.of(warehouse);
        List<WarehouseDto> warehouseDtos = List.of(warehouseDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Warehouse> warehousePage =
                new PageImpl<>(warehouses, pageable, warehouses.size());

        Mockito.when(warehouseRepository.findAll(pageable))
                .thenReturn(warehousePage);

        Mockito.when(modelMapper.map(
                Mockito.eq(warehouses),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(warehouseDtos);

        List<WarehouseDto> response = warehouseService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("Admin");
        warehouse.setStatus(true); // initial status

        Mockito.when(warehouseRepository.findByIdentifier("Admin"))
                .thenReturn(warehouse);

        warehouseService.toggleStatus("Admin");

        Assertions.assertFalse(warehouse.isStatus(), "Status should be toggled to false");

        Mockito.verify(warehouseRepository)
                .save(warehouse);
    }

    @Test
    void toggleStatusWarehouseNotFoundTest() {

        Mockito.when(warehouseRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> warehouseService.toggleStatus("Admin")
        );

        Assertions.assertEquals("Shelf not found", ex.getMessage());
    }

    @Test
    void findByIdentifierNullTest() {

        Mockito.when(warehouseRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, WarehouseDto.class))
                .thenReturn(null);

        WarehouseDto response = warehouseService.findByIdentifier("Admin");

        Assertions.assertNull(response);
    }

    @Test
    void save_mapperAndRepositoryInvocationTest() {

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH1");

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("WH1");

        Mockito.when(warehouseRepository.findByIdentifier("WH1"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(dto, Warehouse.class))
                .thenReturn(warehouse);
        Mockito.when(warehouseRepository.save(warehouse))
                .thenReturn(warehouse);

        warehouseService.save(dto);

        Mockito.verify(modelMapper).map(dto, Warehouse.class);
        Mockito.verify(warehouseRepository).save(warehouse);
    }

    @Test
    void save_existingWarehouse_doesNotSave() {

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH2");

        Mockito.when(warehouseRepository.findByIdentifier("WH2"))
                .thenReturn(new Warehouse());

        warehouseService.save(dto);

        Mockito.verify(warehouseRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void update_mapperMapsOntoExistingWarehouse() {

        Warehouse existing = new Warehouse();
        existing.setIdentifier("WH3");

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH3");

        Mockito.when(warehouseRepository.findByIdentifier("WH3"))
                .thenReturn(existing);
        Mockito.when(warehouseRepository.save(existing))
                .thenReturn(existing);

        warehouseService.update(dto);

        Mockito.verify(modelMapper).map(dto, existing);
        Mockito.verify(warehouseRepository).save(existing);
    }

    @Test
    void findByIdentifier_mapperInvocationTest() {

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("WH4");

        WarehouseDto dto = new WarehouseDto();
        dto.setIdentifier("WH4");

        Mockito.when(warehouseRepository.findByIdentifier("WH4"))
                .thenReturn(warehouse);
        Mockito.when(modelMapper.map(warehouse, WarehouseDto.class))
                .thenReturn(dto);

        WarehouseDto response = warehouseService.findByIdentifier("WH4");

        Mockito.verify(modelMapper).map(warehouse, WarehouseDto.class);
        Assertions.assertEquals("WH4", response.getIdentifier());
    }

    @Test
    void findAll_mapperInvocationTest() {

        Pageable pageable = PageRequest.of(0, 10);
        List<Warehouse> warehouses = List.of(new Warehouse());

        Page<Warehouse> page =
                new PageImpl<>(warehouses, pageable, warehouses.size());

        Mockito.when(warehouseRepository.findAll(pageable))
                .thenReturn(page);

        Mockito.when(modelMapper.map(
                Mockito.eq(warehouses),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of(new WarehouseDto()));

        warehouseService.findAll(pageable);

        Mockito.verify(modelMapper).map(
                Mockito.eq(warehouses),
                Mockito.any(java.lang.reflect.Type.class)
        );
    }

    @Test
    void toggleStatus_saveAlwaysCalled() {

        Warehouse warehouse = new Warehouse();
        warehouse.setIdentifier("WH5");
        warehouse.setStatus(false);

        Mockito.when(warehouseRepository.findByIdentifier("WH5"))
                .thenReturn(warehouse);

        warehouseService.toggleStatus("WH5");

        Mockito.verify(warehouseRepository).save(warehouse);
        Assertions.assertTrue(warehouse.isStatus());
    }
}
