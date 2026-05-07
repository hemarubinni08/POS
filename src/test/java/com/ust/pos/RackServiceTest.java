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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class RackServiceTest {

    @InjectMocks
    private RackServiceImpl rackService;

    @Mock
    private RackRepository rackRepository;

    // ================= SAVE =================

    @Test
    void save_success() {

        RackDto dto = new RackDto();
        dto.setName("Rack1");
        dto.setStatus(true);

        Mockito.when(rackRepository.findByIdentifier("Rack1"))
                .thenReturn(null);

        Rack saved = new Rack();
        saved.setIdentifier("Rack1");
        saved.setName("Rack1");
        saved.setStatus(true);

        Mockito.when(rackRepository.save(Mockito.any(Rack.class)))
                .thenReturn(saved);

        RackDto response = rackService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Rack1", response.getIdentifier());
        Assertions.assertEquals("Rack saved successfully", response.getMessage());
    }

    @Test
    void save_failure_emptyName() {

        RackDto dto = new RackDto();

        RackDto response = rackService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Rack name is required", response.getMessage());
    }

    @Test
    void save_failure_exists() {

        RackDto dto = new RackDto();
        dto.setName("Rack1");

        Mockito.when(rackRepository.findByIdentifier("Rack1"))
                .thenReturn(new Rack());

        RackDto response = rackService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Rack already exists", response.getMessage());
    }

    // ================= UPDATE =================

    @Test
    void update_success() {

        RackDto dto = new RackDto();
        dto.setIdentifier("R1");
        dto.setStatus(true);
        dto.setShelfIdentifiers(List.of("S1", "S2"));

        Rack rack = new Rack();
        rack.setIdentifier("R1");
        rack.setStatus(false);

        Mockito.when(rackRepository.findByIdentifier("R1"))
                .thenReturn(rack);

        Mockito.when(rackRepository.save(rack))
                .thenReturn(rack);

        RackDto response = rackService.update(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Rack updated successfully", response.getMessage());
    }

    @Test
    void update_failure_notFound() {

        RackDto dto = new RackDto();
        dto.setIdentifier("R1");

        Mockito.when(rackRepository.findByIdentifier("R1"))
                .thenReturn(null);

        RackDto response = rackService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Rack not found", response.getMessage());
    }

    @Test
    void update_failure_missingId() {

        RackDto dto = new RackDto();

        RackDto response = rackService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Identifier missing", response.getMessage());
    }

    // ================= FIND =================

    @Test
    void findByIdentifier_success() {

        Rack rack = new Rack();
        rack.setIdentifier("R1");
        rack.setName("Rack1");
        rack.setStatus(true);

        Mockito.when(rackRepository.findByIdentifier("R1"))
                .thenReturn(rack);

        RackDto response = rackService.findByIdentifier("R1");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("R1", response.getIdentifier());
    }

    @Test
    void findByIdentifier_notFound() {

        Mockito.when(rackRepository.findByIdentifier("R1"))
                .thenReturn(null);

        RackDto response = rackService.findByIdentifier("R1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Rack not found", response.getMessage());
    }

    // ================= FIND ALL =================

    @Test
    void findAll_test() {

        Rack rack = new Rack();
        rack.setIdentifier("R1");
        rack.setName("Rack1");
        rack.setStatus(true);

        Mockito.when(rackRepository.findAll())
                .thenReturn(List.of(rack));

        List<RackDto> result = rackService.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("R1", result.get(0).getIdentifier());
    }

    // ================= ACTIVE =================

    @Test
    void getActiveRacks_test() {

        Rack active = new Rack();
        active.setIdentifier("R1");
        active.setStatus(true);

        Rack inactive = new Rack();
        inactive.setIdentifier("R2");
        inactive.setStatus(false);

        Mockito.when(rackRepository.findAll())
                .thenReturn(List.of(active, inactive));

        List<RackDto> result = rackService.getActiveRacks();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("R1", result.get(0).getIdentifier());
    }

    // ================= DELETE =================

    @Test
    void delete_test() {

        Mockito.doNothing()
                .when(rackRepository)
                .deleteByIdentifier("R1");

        rackService.delete("R1");

        Mockito.verify(rackRepository).deleteByIdentifier("R1");
    }

    // ================= TOGGLE =================

    @Test
    void toggle_success() {

        Rack rack = new Rack();
        rack.setIdentifier("R1");
        rack.setStatus(true);

        Mockito.when(rackRepository.findByIdentifier("R1"))
                .thenReturn(rack);

        Mockito.when(rackRepository.save(rack))
                .thenReturn(rack);

        RackDto response = rackService.toggleStatus("R1");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Status updated", response.getMessage());
    }

    @Test
    void toggle_failure() {

        Mockito.when(rackRepository.findByIdentifier("R1"))
                .thenReturn(null);

        RackDto response = rackService.toggleStatus("R1");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Rack not found", response.getMessage());
    }
}