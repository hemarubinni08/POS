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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @InjectMocks
    private UnitServiceImpl unitService;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {

        //request data
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U101");

        Mockito.when(unitRepository.findByIdentifier("U101")).thenReturn(null);
        Unit unit = new Unit();

        Mockito.when(modelMapper.map(unitDto, Unit.class)).thenReturn(unit);
        Mockito.when(unitRepository.save(unit)).thenReturn(unit);
        UnitDto response = unitService.save(unitDto);
        Assertions.assertEquals("U101", response.getIdentifier());
        Assertions.assertEquals(true, response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        //request data
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U101");
        Unit unit = new Unit();
        Mockito.when(unitRepository.findByIdentifier("U101")).thenReturn(unit);
        UnitDto response = unitService.save(unitDto);
        Assertions.assertEquals("U101", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage(), "Message cannot be null");
        Assertions.assertEquals(false, response.isSuccess());
    }

    @Test
    void findByIdentifierTest() {
        Unit unit = new Unit();
        unit.setIdentifier("U101");
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U101");
        Mockito.when(unitRepository.findByIdentifier("U101")).thenReturn(unit);
        Mockito.when(modelMapper.map(unit, UnitDto.class)).thenReturn(unitDto);
        UnitDto response = unitService.findByIdentifier("U101");
        Assertions.assertEquals("U101", response.getIdentifier());
    }

    @Test
    void updateTest() {
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U101");
        Unit existingUnit = new Unit();
        existingUnit.setIdentifier("U101");
        Mockito.when(unitRepository.findByIdentifier("U101"))
                .thenReturn(existingUnit);
        Mockito.when(unitRepository.save(existingUnit))
                .thenReturn(existingUnit);
        UnitDto response = unitService.update(unitDto);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U101");
        Mockito.when(unitRepository.findByIdentifier("U101"))
                .thenReturn(null);
        UnitDto response = unitService.update(unitDto);
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(unitRepository)
                .deleteByIdentifier("U101");
        unitService.delete("U101");
        Mockito.verify(unitRepository).deleteByIdentifier("U101");
    }

    @Test
    void findAllTest() {

        // ARRANGE
        Unit unit = new Unit();
        unit.setIdentifier("U101");

        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U101");

        List<Unit> units = List.of(unit);
        List<UnitDto> unitDtos = List.of(unitDto);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Unit> unitPage =
                new PageImpl<>(units, pageable, units.size());

        Mockito.when(unitRepository.findAll(pageable))
                .thenReturn(unitPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(units),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(unitDtos);

        // ACT
        List<UnitDto> response = unitService.findAll(pageable);

        // ASSERT
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("U101", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusSuccessTest() {

        // ARRANGE
        Unit unit = new Unit();
        unit.setIdentifier("Admin");
        unit.setStatus(false); // currently inactive
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("Admin");
        unitDto.setStatus(true); // after toggle should be active
        // MOCK
        // unit exists in DB
        Mockito.when(unitRepository.findByIdentifier("Admin")).thenReturn(unit);
        // after save, unit status is updated
        Mockito.when(unitRepository.save(unit)).thenReturn(unit);
        // mapper returns unitDto
        Mockito.when(modelMapper.map(unit, UnitDto.class)).thenReturn(unitDto);
        // ACT
        UnitDto response = unitService.toggleStatus("Admin", true);
        // ASSERT
        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isStatus()); // status should be true now
    }

    @Test
    void changeUnitStatusFailureTest() {

        // ARRANGE - unit does NOT exist in DB
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("Admin");
        // MOCK
        // unit not found → returns null
        Mockito.when(unitRepository.findByIdentifier("Admin")).thenReturn(null);
        // ACT
        UnitDto response = unitService.toggleStatus("Admin", true);
        // ASSERT
        // since unit is null, modelMapper.map(null, UnitDto.class) returns null
        Assertions.assertNull(response);
        // verify save was NEVER called because unit was null
        Mockito.verify(unitRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void findActiveUnitsTest() {

        // ARRANGE
        Unit unit = new Unit();
        unit.setIdentifier("PROD_01");
        unit.setStatus(true);

        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("PROD_01");
        unitDto.setStatus(true);

        List<Unit> activeUnits = List.of(unit);
        List<UnitDto> activeUnitDtos = List.of(unitDto);

        Mockito.when(unitRepository.findByStatusTrue()).thenReturn(activeUnits);
        Mockito.when(modelMapper.map(
                Mockito.eq(activeUnits),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(activeUnitDtos);

        // ACT
        List<UnitDto> response = unitService.findActiveUnit();

        // ASSERT
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("PROD_01", response.get(0).getIdentifier());
        Assertions.assertTrue(response.get(0).isStatus());
    }
}