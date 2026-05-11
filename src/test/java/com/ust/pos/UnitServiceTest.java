package com.ust.pos;


import com.ust.pos.dto.UnitDto;
import com.ust.pos.modell.Unit;
import com.ust.pos.modell.UnitRepository;
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
        unitDto.setIdentifier("Admin");

        Mockito.when(unitRepository.findByIdentifier("Admin")).thenReturn(null);
        Unit unit = new Unit();
        Mockito.when(modelMapper.map(unitDto, Unit.class)).thenReturn(unit);
        Mockito.when(unitRepository.save(unit)).thenReturn(unit);
        UnitDto response = unitService.save(unitDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNull(response.getMessage());
        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    void saveTestFailure() {

        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("Admin");
        Unit unit = new Unit();

        Mockito.when(unitRepository.findByIdentifier("Admin")).thenReturn(unit);
        UnitDto response = unitService.save(unitDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertNotNull(response.getMessage(), "Message cannot be null");

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void findByIdentifierTest() {
        Unit unit = new Unit();
        unit.setIdentifier("Admin");

        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("Admin");

        Mockito.when(unitRepository.findByIdentifier("Admin")).thenReturn(unit);
        Mockito.when(modelMapper.map(unit, UnitDto.class)).thenReturn(unitDto);

        UnitDto response = unitService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    @Test
    void updateTest() {
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("Admin");

        Unit existingRole = new Unit();
        existingRole.setIdentifier("Admin");

        Mockito.when(unitRepository.findByIdentifier("Admin"))
                .thenReturn(existingRole);
        Mockito.when(unitRepository.save(existingRole))
                .thenReturn(existingRole);

        UnitDto response = unitService.update(unitDto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("Admin");

        Mockito.when(unitRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        UnitDto response = unitService.update(unitDto);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void deleteTest() {

        Mockito.doNothing().when(unitRepository)
                .deleteByIdentifier("Admin");

        unitService.delete("Admin");

        Mockito.verify(unitRepository).deleteByIdentifier("Admin");
    }

    @Test
    void findAllTest() {
        Unit unit = new Unit();
        unit.setIdentifier("Admin");

        UnitDto unitDto = new UnitDto();
        unitDto.setIdentifier("Admin");

        List<Unit> units = List.of(unit);
        List<UnitDto> unitDtos = List.of(unitDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Unit> unitPage =
                new PageImpl<>(units, pageable, units.size());

        Mockito.when(unitRepository.findAll(pageable))
                .thenReturn(unitPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(units),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(unitDtos);

        List<UnitDto> response = unitService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {

        Unit unit = new Unit();
        unit.setIdentifier("Admin");
        unit.setStatus(true); // initial status

        Mockito.when(unitRepository.findByIdentifier("Admin"))
                .thenReturn(unit);

        unitService.toggleStatus("Admin");

        Assertions.assertFalse(unit.isStatus(), "Status should be toggled to false");

        Mockito.verify(unitRepository)
                .save(unit);
    }

    @Test
    void toggleStatusUnitNotFoundTest() {

        Mockito.when(unitRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> unitService.toggleStatus("Admin")
        );

        Assertions.assertEquals("Unit not found", ex.getMessage());
    }

    @Test
    void findByIdentifierNullTest() {

        Mockito.when(unitRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, UnitDto.class))
                .thenReturn(null);

        UnitDto response = unitService.findByIdentifier("Admin");

        Assertions.assertNull(response);
    }

    @Test
    void findAllEmptyTest() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));

        Page<Unit> emptyPage =
                new PageImpl<>(List.of(), pageable, 0);

        Mockito.when(unitRepository.findAll(pageable))
                .thenReturn(emptyPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(List.of()),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of());

        List<UnitDto> response = unitService.findAll(pageable);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void save_mapInvocationTest() {

        UnitDto dto = new UnitDto();
        dto.setIdentifier("KG");

        Unit unit = new Unit();
        unit.setIdentifier("KG");

        Mockito.when(unitRepository.findByIdentifier("KG")).thenReturn(null);
        Mockito.when(modelMapper.map(dto, Unit.class)).thenReturn(unit);
        Mockito.when(unitRepository.save(unit)).thenReturn(unit);

        unitService.save(dto);

        Mockito.verify(modelMapper).map(dto, Unit.class);
        Mockito.verify(unitRepository).save(unit);
    }

    @Test
    void update_mapperCalledOnExistingEntity() {

        Unit existing = new Unit();
        existing.setIdentifier("LTR");

        UnitDto dto = new UnitDto();
        dto.setIdentifier("LTR");

        Mockito.when(unitRepository.findByIdentifier("LTR"))
                .thenReturn(existing);
        Mockito.when(unitRepository.save(existing))
                .thenReturn(existing);

        unitService.update(dto);

        Mockito.verify(modelMapper).map(dto, existing);
        Mockito.verify(unitRepository).save(existing);
    }

    @Test
    void findByIdentifier_mapperInvocationTest() {

        Unit unit = new Unit();
        unit.setIdentifier("PCS");

        UnitDto dto = new UnitDto();
        dto.setIdentifier("PCS");

        Mockito.when(unitRepository.findByIdentifier("PCS"))
                .thenReturn(unit);
        Mockito.when(modelMapper.map(unit, UnitDto.class))
                .thenReturn(dto);

        UnitDto response = unitService.findByIdentifier("PCS");

        Mockito.verify(modelMapper).map(unit, UnitDto.class);
        Assertions.assertEquals("PCS", response.getIdentifier());
    }

    @Test
    void toggleStatus_saveAlwaysCalled() {

        Unit unit = new Unit();
        unit.setIdentifier("BOX");
        unit.setStatus(false);

        Mockito.when(unitRepository.findByIdentifier("BOX"))
                .thenReturn(unit);

        unitService.toggleStatus("BOX");

        Mockito.verify(unitRepository, Mockito.times(1))
                .save(unit);

        Assertions.assertTrue(unit.isStatus());
    }

    @Test
    void findAll_mapperInvocationTest() {

        Pageable pageable = PageRequest.of(0, 10);
        List<Unit> units = List.of(new Unit());

        Page<Unit> page =
                new PageImpl<>(units, pageable, units.size());

        Mockito.when(unitRepository.findAll(pageable)).thenReturn(page);
        Mockito.when(modelMapper.map(
                Mockito.eq(units),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of(new UnitDto()));

        unitService.findAll(pageable);

        Mockito.verify(modelMapper).map(
                Mockito.eq(units),
                Mockito.any(java.lang.reflect.Type.class)
        );
    }

    @Test
    void save_existingUnit_doesNotSaveEntity() {

        UnitDto dto = new UnitDto();
        dto.setIdentifier("GMS");

        Mockito.when(unitRepository.findByIdentifier("GMS"))
                .thenReturn(new Unit());

        unitService.save(dto);

        Mockito.verify(unitRepository, Mockito.never())
                .save(Mockito.any());
    }

}
