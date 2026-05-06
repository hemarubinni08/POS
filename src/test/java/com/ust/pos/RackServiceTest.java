package com.ust.pos;

import com.ust.pos.dto.RackDto;
import com.ust.pos.model.Rack;
import com.ust.pos.model.RackRepository;
import com.ust.pos.rack.service.impl.RackServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RackServiceTest {

    @InjectMocks
    private RackServiceImpl rackService;

    @Mock
    private RackRepository rackRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void createRackTest() {
        RackDto dto = new RackDto();
        dto.setIdentifier("R1");

        Rack rack = new Rack();

        Mockito.when(rackRepository.existsByIdentifier("R1")).thenReturn(false);
        Mockito.when(modelMapper.map(dto, Rack.class)).thenReturn(rack);

        RackDto response = rackService.createRack(dto);

        Assertions.assertEquals("R1", response.getIdentifier());
        Mockito.verify(rackRepository).save(rack);
    }

    @Test
    void createRackDuplicateTest() {
        RackDto dto = new RackDto();
        dto.setIdentifier("R1");

        Mockito.when(rackRepository.existsByIdentifier("R1")).thenReturn(true);

        RackDto response = rackService.createRack(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Rack already exists", response.getMessage());
    }

    @Test
    void updateRackTest() {
        RackDto dto = new RackDto();
        dto.setIdentifier("R1");

        Rack rack = new Rack();

        Mockito.when(modelMapper.map(dto, Rack.class)).thenReturn(rack);

        RackDto response = rackService.updateRack(dto);

        Assertions.assertEquals("R1", response.getIdentifier());
        Mockito.verify(rackRepository).save(rack);
    }

    @Test
    void getRackTest() {
        Rack rack = new Rack();
        rack.setIdentifier("R1");

        Mockito.when(rackRepository.findById(1L)).thenReturn(Optional.of(rack));

        RackDto response = rackService.getRack(1L);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getRackNotFoundTest() {
        Mockito.when(rackRepository.findById(1L)).thenReturn(Optional.empty());

        RackDto response = rackService.getRack(1L);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Rack not found", response.getMessage());
    }

    @Test
    void getAllRacksTest() {
        Rack rack = new Rack();
        rack.setIdentifier("R1");

        RackDto dto = new RackDto();
        dto.setIdentifier("R1");

        Mockito.when(rackRepository.findAll()).thenReturn(List.of(rack));
        Mockito.when(modelMapper.map(rack, RackDto.class)).thenReturn(dto);

        List<RackDto> response = rackService.getAllRacks();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("R1", response.get(0).getIdentifier());
    }

    @Test
    void deleteRackTest() {
        Mockito.doNothing().when(rackRepository).deleteById(1L);

        boolean response = rackService.deleteRack(1L);

        Assertions.assertTrue(response);
        Mockito.verify(rackRepository).deleteById(1L);
    }
}