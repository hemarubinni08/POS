package com.ust.pos;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.model.Unit;
import com.ust.pos.model.UnitRepository;
import com.ust.pos.unit.service.impl.UnitServiceImpl;
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
public class UnitServiceTest {

    @InjectMocks
    private UnitServiceImpl unitService;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE =================
    @Test
    void saveTest() {

        UnitDto dto = new UnitDto();
        dto.setUnitName("KG");

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(null);

        UnitDto response = unitService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("KG", response.getIdentifier());
    }

    @Test
    void saveTestFailure_EmptyName() {

        UnitDto dto = new UnitDto();

        UnitDto response = unitService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Unit name is required", response.getMessage());
    }

    @Test
    void saveTestFailure_AlreadyExists() {

        UnitDto dto = new UnitDto();
        dto.setUnitName("KG");

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(new Unit());

        UnitDto response = unitService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Unit already exists", response.getMessage());
    }

    // ================= FIND BY IDENTIFIER =================
    @Test
    void findByIdentifierTest() {

        Unit unit = new Unit();
        unit.setIdentifier("KG");

        UnitDto mapped = new UnitDto();
        mapped.setIdentifier("KG");

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(unit);

        Mockito.when(modelMapper.map(unit, UnitDto.class))
                .thenReturn(mapped);

        UnitDto response = unitService.findByIdentifier("KG");

        Assertions.assertEquals("KG", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(null);

        UnitDto response = unitService.findByIdentifier("KG");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Unit not found", response.getMessage());
    }

    // ================= UPDATE =================
    @Test
    void updateTest() {

        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");
        dto.setStatus(true);

        Unit existing = new Unit();
        existing.setIdentifier("KG");

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(existing);

        Mockito.when(unitRepository.save(existing))
                .thenReturn(existing);

        UnitDto response = unitService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure_NotFound() {

        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(null);

        UnitDto response = unitService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Unit not found", response.getMessage());
    }

    @Test
    void updateTestFailure_InvalidIdentifier() {

        UnitDto dto = new UnitDto();

        UnitDto response = unitService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Invalid identifier", response.getMessage());
    }

    // ================= DELETE =================
    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(unitRepository)
                .deleteByIdentifier("KG");

        unitService.delete("KG");

        Mockito.verify(unitRepository).deleteByIdentifier("KG");
    }

    // ================= FIND ALL =================
    @Test
    void findAllTest() {

        Unit unit = new Unit();
        unit.setIdentifier("KG");

        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        List<Unit> units = List.of(unit);
        List<UnitDto> dtos = List.of(dto);

        Mockito.when(unitRepository.findAll()).thenReturn(units);

        Mockito.when(modelMapper.map(
                Mockito.eq(units),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(dtos);

        List<UnitDto> response = unitService.findAll();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("KG", response.get(0).getIdentifier());
    }

    // ================= TOGGLE =================
    @Test
    void toggleStatusTest() {

        Unit unit = new Unit();
        unit.setIdentifier("KG");
        unit.setStatus(true);

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(unit);

        Mockito.when(unitRepository.save(unit))
                .thenReturn(unit);

        Mockito.when(modelMapper.map(unit, UnitDto.class))
                .thenReturn(new UnitDto());

        UnitDto response = unitService.toggleStatus("KG");

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void toggleStatus_NotFound() {

        Mockito.when(unitRepository.findByIdentifier("KG"))
                .thenReturn(null);

        UnitDto response = unitService.toggleStatus("KG");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Unit not found", response.getMessage());
    }

    // ================= ACTIVE =================
    @Test
    void findActiveUnitsTest() {

        Unit active = new Unit();
        active.setIdentifier("KG");
        active.setStatus(true);

        Unit inactive = new Unit();
        inactive.setIdentifier("L");
        inactive.setStatus(false);

        Mockito.when(unitRepository.findAll())
                .thenReturn(List.of(active, inactive));

        Mockito.when(modelMapper.map(active, UnitDto.class))
                .thenReturn(new UnitDto());

        List<UnitDto> result = unitService.findActiveUnits();

        Assertions.assertEquals(1, result.size());
    }
}