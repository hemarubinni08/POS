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
import org.springframework.data.domain.*;
import java.util.ArrayList;
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
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U1");
        Mockito.when(unitRepository.findByIdentifier("U1")).thenReturn(null);
        Unit unit = new Unit();
        Mockito.when(modelMapper.map(unitDto, Unit.class)).thenReturn(unit);
        Mockito.when(unitRepository.save(unit)).thenReturn(unit);
        UnitDto response = unitService.save(unitDto);
        Assertions.assertEquals("U1", response.getIdentifier());
    }

    @Test
    void saveTestFailure() {
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U1");
        Unit existingUnit = new Unit();
        Mockito.when(unitRepository.findByIdentifier("U1")).thenReturn(existingUnit);
        UnitDto response = unitService.save(unitDto);
        Assertions.assertEquals("U1", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void updateTest() {
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U1");
        Unit existingUnit = new Unit();
        existingUnit.setIdentifier("U1");
        Mockito.when(unitRepository.findByIdentifier("U1"))
                .thenReturn(existingUnit);
        Mockito.when(unitRepository.save(existingUnit))
                .thenReturn(existingUnit);
        UnitDto response = unitService.update(unitDto);
        Assertions.assertEquals("U1", response.getIdentifier());
    }

    @Test
    void updateTestFailure() {
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U1");
        Mockito.when(unitRepository.findByIdentifier("U1"))
                .thenReturn(null);
        UnitDto response = unitService.update(unitDto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(unitRepository).deleteByIdentifier("U1");
        boolean result = unitService.delete("U1");
        Mockito.verify(unitRepository).deleteByIdentifier("U1");
        Assertions.assertTrue(result);
    }

    @Test
    void findAllTest() {
        Unit unit = new Unit();
        unit.setIdentifier("U1");
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U1");
        List<Unit> units = List.of(unit);
        List<UnitDto> unitDtos = List.of(unitDto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Unit> unitPage =
                new PageImpl<>(units, pageable, units.size());
        Mockito.when(unitRepository.findAll(pageable))
                .thenReturn(unitPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(units),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(unitDtos);
        List<UnitDto> response = unitService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("U1", response.get(0).getIdentifier());
    }

    @Test
    void findByIdentifierTest() {
        Unit unit = new Unit();
        unit.setIdentifier("U1");
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("U1");
        Mockito.when(unitRepository.findByIdentifier("U1")).thenReturn(unit);
        Mockito.when(modelMapper.map(unit, UnitDto.class)).thenReturn(unitDto);
        UnitDto response = unitService.findByIdentifier("U1");
        Assertions.assertEquals("U1", response.getIdentifier());
    }
}