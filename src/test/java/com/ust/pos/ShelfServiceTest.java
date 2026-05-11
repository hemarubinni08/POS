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
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ShelfServiceTest {

    @InjectMocks
    private ShelfServiceImpl shelfService;
    @Mock
    private ShelfRepository shelfRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest_Success() {
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        Mockito.when(shelfRepository.findByIdentifier("S001"))
                .thenReturn(null);
        Shelf shelf = new Shelf();
        Mockito.when(modelMapper.map(dto, Shelf.class))
                .thenReturn(shelf);
        Mockito.when(shelfRepository.save(shelf))
                .thenReturn(shelf);
        ShelfDto response = shelfService.save(dto);
        Mockito.verify(modelMapper).map(dto, Shelf.class);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTest_Failure() {
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        Mockito.when(shelfRepository.findByIdentifier("S001"))
                .thenReturn(new Shelf());
        ShelfDto response = shelfService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void updateTest_Success() {
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S001");
        Mockito.when(shelfRepository.findByIdentifier("S001"))
                .thenReturn(shelf);
        Mockito.when(shelfRepository.save(shelf))
                .thenReturn(shelf);
        ShelfDto response = shelfService.update(dto);
        Mockito.verify(modelMapper).map(dto, shelf);
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_Failure_NotFound() {
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        Mockito.when(shelfRepository.findByIdentifier("S001"))
                .thenReturn(null);
        ShelfDto response = shelfService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(shelfRepository)
                .deleteByIdentifier("S001");
        boolean result = shelfService.delete("S001");
        Mockito.verify(shelfRepository)
                .deleteByIdentifier("S001");
        Assertions.assertTrue(result);
    }

    @Test
    void findAllTest_NonEmpty() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S001");
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        List<Shelf> shelves = List.of(shelf);
        List<ShelfDto> shelfDtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Shelf> shelfPage =
                new PageImpl<>(shelves, pageable, shelves.size());
        Mockito.when(shelfRepository.findAll(pageable))
                .thenReturn(shelfPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(shelves),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(shelfDtos);
        List<ShelfDto> response = shelfService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("S001", response.get(0).getIdentifier());
    }

    @Test
    void findAllTest_Empty() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Shelf> shelfPage =
                new PageImpl<>(List.of(), pageable, 0);
        Mockito.when(shelfRepository.findAll(pageable))
                .thenReturn(shelfPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(List.of()),
                        Mockito.any(java.lang.reflect.Type.class))).thenReturn(List.of());
        List<ShelfDto> response = shelfService.findAll(pageable);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void findByIdentifierTest_Success() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S001");
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        Mockito.when(shelfRepository.findByIdentifier("S001"))
                .thenReturn(shelf);
        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(dto);
        ShelfDto response = shelfService.findByIdentifier("S001");
        Assertions.assertEquals("S001", response.getIdentifier());
    }

    @Test
    void findByIdentifierTest_NullShelf() {
        Mockito.when(shelfRepository.findByIdentifier("S404"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(null, ShelfDto.class))
                .thenReturn(null);
        ShelfDto response = shelfService.findByIdentifier("S404");
        Assertions.assertNull(response);
    }

    @Test
    void findIfTrueTest_NonEmpty() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S001");
        shelf.setStatus(true);
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        dto.setStatus(true);
        Mockito.when(shelfRepository.findByStatusIsTrue())
                .thenReturn(List.of(shelf));
        Mockito.when(modelMapper.map(
                Mockito.eq(List.of(shelf)),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of(dto));
        List<ShelfDto> response = shelfService.findIfTrue();
        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findIfTrueTest_Empty() {
        Mockito.when(shelfRepository.findByStatusIsTrue())
                .thenReturn(List.of());
        Mockito.when(modelMapper.map(
                Mockito.eq(List.of()),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of());
        List<ShelfDto> response = shelfService.findIfTrue();
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void toggleStatus_FalseToTrue() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S001");
        shelf.setStatus(false);
        Mockito.when(shelfRepository.findByIdentifier("S001"))
                .thenReturn(shelf);
        Mockito.when(shelfRepository.save(shelf))
                .thenReturn(shelf);
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S001");
        dto.setStatus(true);
        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(dto);
        ShelfDto response = shelfService.toggleStatus("S001");
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void toggleStatus_TrueToFalse() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S002");
        shelf.setStatus(true);
        Mockito.when(shelfRepository.findByIdentifier("S002"))
                .thenReturn(shelf);
        Mockito.when(shelfRepository.save(shelf))
                .thenReturn(shelf);
        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S002");
        dto.setStatus(false);
        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(dto);
        ShelfDto response = shelfService.toggleStatus("S002");
        Assertions.assertFalse(response.isStatus());
    }

    @Test
    void toggleStatus_NullShelf_ShouldThrowException() {
        Mockito.when(shelfRepository.findByIdentifier("S404"))
                .thenReturn(null);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> shelfService.toggleStatus("S404")
        );
    }
}