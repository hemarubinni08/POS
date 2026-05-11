package com.ust.pos;

import com.ust.pos.dto.RacksDto;
import com.ust.pos.modell.Racks;
import com.ust.pos.modell.RacksRepository;
import com.ust.pos.racks.service.impl.RacksServiceImpl;
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
 class RacksServiceTest {

    @InjectMocks
    private RacksServiceImpl racksService;

    @Mock
    private RacksRepository racksRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        RacksDto racksDto = new RacksDto();
        racksDto.setIdentifier("R1");

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(null);

        Racks racks = new Racks();
        racks.setIdentifier("R1");

        Mockito.when(modelMapper.map(racksDto, Racks.class))
                .thenReturn(racks);
        Mockito.when(racksRepository.save(racks))
                .thenReturn(racks);

        RacksDto response = racksService.save(racksDto);

        Assertions.assertEquals("R1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure() {
        RacksDto racksDto = new RacksDto();
        racksDto.setIdentifier("R1");

        Racks existingRacks = new Racks();
        existingRacks.setIdentifier("R1");

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(existingRacks);

        RacksDto response = racksService.save(racksDto);

        Assertions.assertEquals("R1", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifierTest() {
        Racks racks = new Racks();
        racks.setIdentifier("R1");

        RacksDto racksDto = new RacksDto();
        racksDto.setIdentifier("R1");

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(racks);
        Mockito.when(modelMapper.map(racks, RacksDto.class))
                .thenReturn(racksDto);

        RacksDto response = racksService.findByIdentifier("R1");

        Assertions.assertEquals("R1", response.getIdentifier());
    }

    @Test
    void updateTest() {
        RacksDto racksDto = new RacksDto();
        racksDto.setIdentifier("R1");

        Racks existingRacks = new Racks();
        existingRacks.setIdentifier("R1");

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(existingRacks);
        Mockito.when(racksRepository.save(existingRacks))
                .thenReturn(existingRacks);

        RacksDto response = racksService.update(racksDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure() {
        RacksDto racksDto = new RacksDto();
        racksDto.setIdentifier("R1");

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(null);

        RacksDto response = racksService.update(racksDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(racksRepository)
                .deleteByIdentifier("R1");

        racksService.delete("R1");

        Mockito.verify(racksRepository)
                .deleteByIdentifier("R1");
    }

    @Test
    void findAllTest() {
        Racks racks = new Racks();
        racks.setIdentifier("R1");

        RacksDto racksDto = new RacksDto();
        racksDto.setIdentifier("R1");

        List<Racks> racksList = List.of(racks);
        List<RacksDto> racksDtos = List.of(racksDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Racks> racksPage =
                new PageImpl<>(racksList, pageable, racksList.size());

        Mockito.when(racksRepository.findAll(pageable))
                .thenReturn(racksPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(racksList),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(racksDtos);

        List<RacksDto> response = racksService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {
        Racks racks = new Racks();
        racks.setIdentifier("R1");
        racks.setStatus(true);

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(racks);

        racksService.toggleStatus("R1");

        Assertions.assertFalse(racks.isStatus());
        Mockito.verify(racksRepository).save(racks);
    }

    @Test
    void findAllActiveTest() {
        Racks racks = new Racks();
        racks.setIdentifier("R1");
        racks.setStatus(true);

        RacksDto racksDto = new RacksDto();
        racksDto.setIdentifier("R1");

        Mockito.when(racksRepository.findByStatusTrue())
                .thenReturn(List.of(racks));
        Mockito.when(modelMapper.map(racks, RacksDto.class))
                .thenReturn(racksDto);

        List<RacksDto> response = racksService.findAllActive();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("R1", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusRacksNotFoundTest() {

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(null);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> racksService.toggleStatus("R1")
        );

        Assertions.assertEquals("Shelf not found", ex.getMessage());
    }

    @Test
    void findByIdentifierNullTest() {

        Mockito.when(racksRepository.findByIdentifier("R1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, RacksDto.class))
                .thenReturn(null);

        RacksDto response = racksService.findByIdentifier("R1");

        Assertions.assertNull(response);
    }

    @Test
    void findAllActive_emptyResultTest() {

        Mockito.when(racksRepository.findByStatusTrue())
                .thenReturn(List.of());

        List<RacksDto> response = racksService.findAllActive();

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());

        Mockito.verify(racksRepository)
                .findByStatusTrue();

        Mockito.verify(modelMapper, Mockito.never())
                .map(Mockito.any(Racks.class), Mockito.eq(RacksDto.class));
    }

    @Test
    void toggleStatus_saveAlwaysCalledTest() {

        Racks racks = new Racks();
        racks.setIdentifier("R2");
        racks.setStatus(false); // initial state

        Mockito.when(racksRepository.findByIdentifier("R2"))
                .thenReturn(racks);

        racksService.toggleStatus("R2");

        Assertions.assertTrue(racks.isStatus(), "Status should be toggled to true");

        Mockito.verify(racksRepository)
                .findByIdentifier("R2");

        Mockito.verify(racksRepository)
                .save(racks);

        Mockito.verifyNoMoreInteractions(racksRepository);
    }

    @Test
    void save_existingRacks_doesNotSave() {

        RacksDto dto = new RacksDto();
        dto.setIdentifier("R3");

        Mockito.when(racksRepository.findByIdentifier("R3"))
                .thenReturn(new Racks());

        racksService.save(dto);

        Mockito.verify(racksRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void update_mapperMapsOntoExistingRacks() {

        Racks existing = new Racks();
        existing.setIdentifier("R4");

        RacksDto dto = new RacksDto();
        dto.setIdentifier("R4");

        Mockito.when(racksRepository.findByIdentifier("R4"))
                .thenReturn(existing);
        Mockito.when(racksRepository.save(existing))
                .thenReturn(existing);

        racksService.update(dto);

        Mockito.verify(modelMapper).map(dto, existing);
        Mockito.verify(racksRepository).save(existing);
    }
}