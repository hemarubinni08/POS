package com.ust.pos;

import com.ust.pos.dto.UnitDto;
import com.ust.pos.model.Unit;
import com.ust.pos.model.UnitRepository;
import com.ust.pos.unit.service.impl.UnitServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
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
    void findByIdentifierTest() {
        Unit unit = new Unit();
        unit.setIdentifier("KG");

        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        Mockito.when(unitRepository.findByIdentifier("KG")).thenReturn(unit);
        Mockito.when(modelMapper.map(unit, UnitDto.class)).thenReturn(dto);

        UnitDto response = unitService.findByIdentifier("KG");

        Assertions.assertEquals("KG", response.getIdentifier());
    }

    @Test
    void saveTest() {
        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        Unit unit = new Unit();

        Mockito.when(unitRepository.findByIdentifier("KG")).thenReturn(null);
        Mockito.when(modelMapper.map(dto, Unit.class)).thenReturn(unit);

        UnitDto response = unitService.save(dto);

        Assertions.assertEquals("KG", response.getIdentifier());
        Mockito.verify(unitRepository).save(unit);
    }

    @Test
    void saveDuplicateTest() {
        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        Unit unit = new Unit();

        Mockito.when(unitRepository.findByIdentifier("KG")).thenReturn(unit);

        UnitDto response = unitService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Unit with identifier - KG already exists", response.getMessage());
    }

    @Test
    void updateTest() {
        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        Unit existing = new Unit();
        existing.setIdentifier("KG");

        Mockito.when(unitRepository.findByIdentifier("KG")).thenReturn(existing);

        Mockito.doAnswer(invocation -> {
            UnitDto source = invocation.getArgument(0);
            Unit target = invocation.getArgument(1);
            target.setIdentifier(source.getIdentifier());
            return null;
        }).when(modelMapper).map(Mockito.any(UnitDto.class), Mockito.any(Unit.class));

        UnitDto response = unitService.update(dto);

        Assertions.assertEquals("KG", response.getIdentifier());
        Mockito.verify(unitRepository).save(existing);
    }

    @Test
    void updateNotFoundTest() {
        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        Mockito.when(unitRepository.findByIdentifier("KG")).thenReturn(null);

        UnitDto response = unitService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Unit with identifier - KG not found", response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing().when(unitRepository).deleteByIdentifier("KG");

        boolean result = unitService.delete("KG");

        Assertions.assertTrue(result);
        Mockito.verify(unitRepository).deleteByIdentifier("KG");
    }

    @Test
    void toggleStatusTest() {
        Unit unit = new Unit();
        unit.setIdentifier("KG");
        unit.setStatus(true);

        UnitDto dto = new UnitDto();

        Mockito.when(unitRepository.findByIdentifier("KG")).thenReturn(unit);
        Mockito.when(modelMapper.map(unit, UnitDto.class)).thenReturn(dto);

        UnitDto response = unitService.toggleStatus("KG");

        Assertions.assertNotNull(response);
        Mockito.verify(unitRepository).save(unit);
    }

    @Test
    void findAllTest() {
        Unit unit = new Unit();
        unit.setIdentifier("KG");

        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        List<Unit> units = List.of(unit);
        List<UnitDto> dtos = List.of(dto);

        Mockito.when(unitRepository.findAll()).thenReturn(units);
        Mockito.when(modelMapper.map(Mockito.eq(units), Mockito.any(Type.class))).thenReturn(dtos);

        List<UnitDto> response = unitService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findIfTrueTest() {
        Unit unit = new Unit();
        unit.setIdentifier("KG");

        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        List<Unit> units = List.of(unit);
        List<UnitDto> dtos = List.of(dto);

        Mockito.when(unitRepository.findByStatusIsTrue()).thenReturn(units);
        Mockito.when(modelMapper.map(Mockito.eq(units), Mockito.any(Type.class))).thenReturn(dtos);

        List<UnitDto> response = unitService.findIfTrue();

        Assertions.assertEquals(1, response.size());
    }
}