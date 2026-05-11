package com.ust.pos;

import com.ust.pos.dto.RackDto;
import com.ust.pos.model.Rack;
import com.ust.pos.model.RackRepository;
import com.ust.pos.model.ShelfRepository;
import com.ust.pos.rack.service.impl.RackServiceImpl;
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
class RackServiceTest {

    @InjectMocks
    private RackServiceImpl rackService;
    @Mock
    private RackRepository rackRepository;
    @Mock
    private ShelfRepository shelfRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest_Success() {
        RackDto dto = new RackDto();
        dto.setIdentifier("R001");
        Mockito.when(rackRepository.findByIdentifier("R001"))
                .thenReturn(null);
        Rack rack = new Rack();
        Mockito.when(modelMapper.map(dto, Rack.class))
                .thenReturn(rack);
        Mockito.when(rackRepository.save(rack))
                .thenReturn(rack);
        RackDto response = rackService.save(dto);
        Mockito.verify(modelMapper).map(dto, Rack.class);
        Assertions.assertEquals("R001", response.getIdentifier());
    }

    @Test
    void saveTest_Failure() {
        RackDto dto = new RackDto();
        dto.setIdentifier("R001");
        Mockito.when(rackRepository.findByIdentifier("R001"))
                .thenReturn(new Rack());
        RackDto response = rackService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTest_Success() {
        RackDto dto = new RackDto();
        dto.setIdentifier("R001");
        Rack rack = new Rack();
        rack.setIdentifier("R001");
        Mockito.when(rackRepository.findByIdentifier("R001"))
                .thenReturn(rack);
        Mockito.when(rackRepository.save(rack))
                .thenReturn(rack);
        RackDto response = rackService.update(dto);
        Mockito.verify(modelMapper).map(dto, rack);
        Assertions.assertEquals("R001", response.getIdentifier());
    }

    @Test
    void updateTest_Failure() {
        RackDto dto = new RackDto();
        dto.setIdentifier("R001");
        Mockito.when(rackRepository.findByIdentifier("R001"))
                .thenReturn(null);
        RackDto response = rackService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(rackRepository)
                .deleteByIdentifier("R001");
        boolean result = rackService.delete("R001");
        Mockito.verify(rackRepository).deleteByIdentifier("R001");
        Assertions.assertTrue(result);
    }

    @Test
    void findAllTest_WithData() {
        Rack rack = new Rack();
        rack.setIdentifier("R001");
        RackDto dto = new RackDto();
        dto.setIdentifier("R001");
        List<Rack> racks = List.of(rack);
        List<RackDto> rackDtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Rack> rackPage =
                new PageImpl<>(racks, pageable, racks.size());
        Mockito.when(rackRepository.findAll(pageable))
                .thenReturn(rackPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(racks),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(rackDtos);
        List<RackDto> response = rackService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("R001", response.get(0).getIdentifier());
    }

    @Test
    void findAllTest_Empty() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Rack> rackPage =
                new PageImpl<>(List.of(), pageable, 0);
        Mockito.when(rackRepository.findAll(pageable))
                .thenReturn(rackPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(List.of()),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(List.of());
        List<RackDto> response = rackService.findAll(pageable);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void findByIdentifierTest_Success() {
        Rack rack = new Rack();
        rack.setIdentifier("R001");
        RackDto dto = new RackDto();
        dto.setIdentifier("R001");
        Mockito.when(rackRepository.findByIdentifier("R001"))
                .thenReturn(rack);
        Mockito.when(modelMapper.map(rack, RackDto.class))
                .thenReturn(dto);
        RackDto response = rackService.findByIdentifier("R001");
        Assertions.assertEquals("R001", response.getIdentifier());
    }

    @Test
    void findByIdentifierTest_Null() {
        Mockito.when(rackRepository.findByIdentifier("R404"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(null, RackDto.class))
                .thenReturn(null);
        RackDto response = rackService.findByIdentifier("R404");
        Assertions.assertNull(response);
    }

    @Test
    void toggleStatus_FalseToTrue() {
        Rack rack = new Rack();
        rack.setIdentifier("R001");
        rack.setStatus(false);
        Mockito.when(rackRepository.findByIdentifier("R001"))
                .thenReturn(rack);
        Mockito.when(rackRepository.save(rack))
                .thenReturn(rack);
        RackDto dto = new RackDto();
        dto.setIdentifier("R001");
        dto.setStatus(true);
        Mockito.when(modelMapper.map(rack, RackDto.class))
                .thenReturn(dto);
        RackDto response = rackService.toggleStatus("R001");
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void toggleStatus_TrueToFalse() {
        Rack rack = new Rack();
        rack.setIdentifier("R002");
        rack.setStatus(true);
        Mockito.when(rackRepository.findByIdentifier("R002"))
                .thenReturn(rack);
        Mockito.when(rackRepository.save(rack))
                .thenReturn(rack);
        RackDto dto = new RackDto();
        dto.setIdentifier("R002");
        dto.setStatus(false);
        Mockito.when(modelMapper.map(rack, RackDto.class))
                .thenReturn(dto);
        RackDto response = rackService.toggleStatus("R002");
        Assertions.assertFalse(response.isStatus());
    }

    @Test
    void toggleStatus_NullRack_ShouldThrowException() {
        Mockito.when(rackRepository.findByIdentifier("R404"))
                .thenReturn(null);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> rackService.toggleStatus("R404"));
    }
}