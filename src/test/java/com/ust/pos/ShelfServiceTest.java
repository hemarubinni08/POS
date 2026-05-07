package com.ust.pos;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.model.Shelf;
import com.ust.pos.model.ShelfRepository;
import com.ust.pos.shelf.service.impl.ShelfServiceImpl;
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
class ShelfServiceTest {

    @InjectMocks
    private ShelfServiceImpl shelfService;

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private ModelMapper modelMapper;

    // ================= SAVE =================

    @Test
    void save_success() {

        ShelfDto dto = new ShelfDto();
        dto.setName("Admin");

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        Shelf shelf = new Shelf();
        shelf.setIdentifier("Admin");
        shelf.setName("Admin");

        Mockito.when(modelMapper.map(dto, Shelf.class))
                .thenReturn(shelf);

        Mockito.when(shelfRepository.save(Mockito.any(Shelf.class)))
                .thenReturn(shelf);

        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(new ShelfDto());

        ShelfDto response = shelfService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Shelf saved successfully", response.getMessage());
    }

    @Test
    void save_failure_emptyName() {

        ShelfDto dto = new ShelfDto();

        ShelfDto response = shelfService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Shelf name is required", response.getMessage());
    }

    @Test
    void save_failure_exists() {

        ShelfDto dto = new ShelfDto();
        dto.setName("Admin");

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(new Shelf());

        ShelfDto response = shelfService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Shelf already exists", response.getMessage());
    }

    // ================= FIND =================

    @Test
    void findByIdentifier_success() {

        Shelf shelf = new Shelf();
        shelf.setIdentifier("Admin");

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(shelf);

        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(new ShelfDto());

        ShelfDto response = shelfService.findByIdentifier("Admin");

        Assertions.assertNotNull(response);
    }

    @Test
    void findByIdentifier_failure() {

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        ShelfDto response = shelfService.findByIdentifier("Admin");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Shelf not found", response.getMessage());
    }

    // ================= UPDATE =================

    @Test
    void update_success() {

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("Admin");
        dto.setStatus(true);

        Shelf shelf = new Shelf();
        shelf.setIdentifier("Admin");

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(shelf);

        Mockito.when(shelfRepository.save(shelf))
                .thenReturn(shelf);

        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(new ShelfDto());

        ShelfDto response = shelfService.update(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Shelf updated successfully", response.getMessage());
    }

    @Test
    void update_failure() {

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("Admin");

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        ShelfDto response = shelfService.update(dto);

        Assertions.assertFalse(response.isSuccess());
    }

    // ================= DELETE =================

    @Test
    void delete_test() {

        Mockito.doNothing()
                .when(shelfRepository)
                .deleteByIdentifier("Admin");

        shelfService.delete("Admin");

        Mockito.verify(shelfRepository).deleteByIdentifier("Admin");
    }

    // ================= FIND ALL =================

    @Test
    void findAll_test() {

        Shelf shelf = new Shelf();

        Mockito.when(shelfRepository.findAll())
                .thenReturn(List.of(shelf));

        Mockito.when(modelMapper.map(
                Mockito.anyList(),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of(new ShelfDto()));

        List<ShelfDto> response = shelfService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    // ================= ACTIVE =================

    @Test
    void getActiveShelves_test() {

        Shelf shelf = new Shelf();
        shelf.setStatus(true);

        Mockito.when(shelfRepository.findByStatusTrue())
                .thenReturn(List.of(shelf));

        Mockito.when(modelMapper.map(
                Mockito.anyList(),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of(new ShelfDto()));

        List<ShelfDto> response = shelfService.getActiveShelves();

        Assertions.assertEquals(1, response.size());
    }

    // ================= TOGGLE =================

    @Test
    void toggle_success() {

        Shelf shelf = new Shelf();
        shelf.setStatus(true);

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(shelf);

        Mockito.when(shelfRepository.save(shelf))
                .thenReturn(shelf);

        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(new ShelfDto());

        ShelfDto response = shelfService.toggleStatus("Admin");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Status updated successfully", response.getMessage());
    }

    @Test
    void toggle_failure() {

        Mockito.when(shelfRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        ShelfDto response = shelfService.toggleStatus("Admin");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Shelf not found", response.getMessage());
    }
}