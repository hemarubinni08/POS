package com.ust.pos;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.model.Shelf;
import com.ust.pos.model.ShelfRepository;
import com.ust.pos.shelf.service.impl.ShelfServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ShelfServiceTest {

    @InjectMocks
    private ShelfServiceImpl shelfService;

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void createShelfTest() {
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S1");

        Shelf shelf = new Shelf();

        Mockito.when(shelfRepository.existsByIdentifier("S1")).thenReturn(false);
        Mockito.when(modelMapper.map(dto, Shelf.class)).thenReturn(shelf);

        ShelfDto response = shelfService.createShelf(dto);

        Assertions.assertEquals("S1", response.getIdentifier());
        Mockito.verify(shelfRepository).save(shelf);
    }

    @Test
    void createShelfDuplicateTest() {
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S1");

        Mockito.when(shelfRepository.existsByIdentifier("S1")).thenReturn(true);

        ShelfDto response = shelfService.createShelf(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Shelf already exists", response.getMessage());
    }

    @Test
    void updateShelfTest() {
        ShelfDto dto = new ShelfDto();
        dto.setId(1L);
        dto.setIdentifier("S1");

        Shelf shelf = new Shelf();
        shelf.setIdentifier("OLD");

        Mockito.when(shelfRepository.findById(1L)).thenReturn(Optional.of(shelf));

        Mockito.doAnswer(invocation -> {
            Shelf source = invocation.getArgument(0);
            ShelfDto target = invocation.getArgument(1);
            target.setIdentifier(source.getIdentifier());
            return null;
        }).when(modelMapper).map(Mockito.any(Shelf.class), Mockito.any(ShelfDto.class));

        ShelfDto response = shelfService.updateShelf(dto);

        Assertions.assertTrue(response.isSuccess());
        Mockito.verify(shelfRepository).save(shelf);
    }

    @Test
    void updateShelfNotFoundTest() {
        ShelfDto dto = new ShelfDto();
        dto.setId(1L);

        Mockito.when(shelfRepository.findById(1L)).thenReturn(Optional.empty());

        ShelfDto response = shelfService.updateShelf(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(ShelfServiceImpl.SHELF_NOT_FOUND, response.getMessage());
    }

    @Test
    void getShelfTest() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");

        Mockito.when(shelfRepository.findById(1L)).thenReturn(Optional.of(shelf));

        Mockito.doAnswer(invocation -> {
            Shelf source = invocation.getArgument(0);
            ShelfDto target = invocation.getArgument(1);
            target.setIdentifier(source.getIdentifier());
            return null;
        }).when(modelMapper).map(Mockito.any(Shelf.class), Mockito.any(ShelfDto.class));

        ShelfDto response = shelfService.getShelf(1L);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("S1", response.getIdentifier());
    }

    @Test
    void getShelfNotFoundTest() {
        Mockito.when(shelfRepository.findById(1L)).thenReturn(Optional.empty());

        ShelfDto response = shelfService.getShelf(1L);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(ShelfServiceImpl.SHELF_NOT_FOUND, response.getMessage());
    }

    @Test
    void getAllShelvesTest() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S1");

        Mockito.when(shelfRepository.findAll()).thenReturn(List.of(shelf));
        Mockito.when(modelMapper.map(shelf, ShelfDto.class)).thenReturn(dto);

        List<ShelfDto> response = shelfService.getAllShelves();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("S1", response.get(0).getIdentifier());
    }

    @Test
    void deleteShelfTest() {
        Mockito.doNothing().when(shelfRepository).deleteById(1L);

        boolean response = shelfService.deleteShelf(1L);

        Assertions.assertTrue(response);
        Mockito.verify(shelfRepository).deleteById(1L);
    }

    @Test
    void toggleStatusTest() {
        Shelf shelf = new Shelf();
        shelf.setActive(true);

        Mockito.when(shelfRepository.findById(1L)).thenReturn(Optional.of(shelf));

        Mockito.doAnswer(invocation -> {
            Shelf source = invocation.getArgument(0);
            ShelfDto target = invocation.getArgument(1);
            target.setActive(source.isActive());
            return null;
        }).when(modelMapper).map(Mockito.any(Shelf.class), Mockito.any(ShelfDto.class));

        ShelfDto response = shelfService.toggleStatus(1L);

        Assertions.assertTrue(response.isSuccess());
        Mockito.verify(shelfRepository).save(shelf);
    }

    @Test
    void toggleStatusNotFoundTest() {
        Mockito.when(shelfRepository.findById(1L)).thenReturn(Optional.empty());

        ShelfDto response = shelfService.toggleStatus(1L);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(ShelfServiceImpl.SHELF_NOT_FOUND, response.getMessage());
    }

    @Test
    void getActiveShelvesTest() {

        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");
        shelf.setActive(true);

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S1");
        dto.setActive(true);

        Mockito.when(shelfRepository.findByActiveTrue()).thenReturn(List.of(shelf));
        Mockito.when(modelMapper.map(shelf, ShelfDto.class)).thenReturn(dto);

        List<ShelfDto> response = shelfService.getActiveShelves();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("S1", response.get(0).getIdentifier());
        Assertions.assertTrue(response.get(0).isActive());
    }
}