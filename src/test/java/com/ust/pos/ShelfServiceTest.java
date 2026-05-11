package com.ust.pos;

import com.ust.pos.dto.ShelfDto;
import com.ust.pos.modell.Shelf;
import com.ust.pos.modell.ShelfRepository;
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
    void saveTest() {
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setIdentifier("S1");

        Mockito.when(shelfRepository.findByIdentifier("S1")).thenReturn(null);

        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");

        Mockito.when(modelMapper.map(shelfDto, Shelf.class)).thenReturn(shelf);
        Mockito.when(shelfRepository.save(shelf)).thenReturn(shelf);

        ShelfDto response = shelfService.save(shelfDto);

        Assertions.assertEquals("S1", response.getIdentifier());
        Assertions.assertNull(response.getMessage());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setIdentifier("S1");

        Shelf existingShelf = new Shelf();
        existingShelf.setIdentifier("S1");

        Mockito.when(shelfRepository.findByIdentifier("S1"))
                .thenReturn(existingShelf);

        ShelfDto response = shelfService.save(shelfDto);

        Assertions.assertEquals("S1", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifierTest() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");

        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setIdentifier("S1");

        Mockito.when(shelfRepository.findByIdentifier("S1"))
                .thenReturn(shelf);
        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(shelfDto);

        ShelfDto response = shelfService.findByIdentifier("S1");

        Assertions.assertEquals("S1", response.getIdentifier());
    }

    @Test
    void updateTest() {
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setIdentifier("S1");

        Shelf existingShelf = new Shelf();
        existingShelf.setIdentifier("S1");

        Mockito.when(shelfRepository.findByIdentifier("S1"))
                .thenReturn(existingShelf);
        Mockito.when(shelfRepository.save(existingShelf))
                .thenReturn(existingShelf);

        ShelfDto response = shelfService.update(shelfDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure() {
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setIdentifier("S1");

        Mockito.when(shelfRepository.findByIdentifier("S1"))
                .thenReturn(null);

        ShelfDto response = shelfService.update(shelfDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(shelfRepository)
                .deleteByIdentifier("S1");

        shelfService.delete("S1");

        Mockito.verify(shelfRepository)
                .deleteByIdentifier("S1");
    }

    @Test
    void findAllTest() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");

        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setIdentifier("S1");

        List<Shelf> shelves = List.of(shelf);
        List<ShelfDto> shelfDtos = List.of(shelfDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Shelf> shelfPage =
                new PageImpl<>(shelves, pageable, shelves.size());

        Mockito.when(shelfRepository.findAll(pageable))
                .thenReturn(shelfPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(shelves),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(shelfDtos);

        List<ShelfDto> response = shelfService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");
        shelf.setStatus(true);

        Mockito.when(shelfRepository.findByIdentifier("S1"))
                .thenReturn(shelf);

        shelfService.toggleStatus("S1");

        Assertions.assertFalse(shelf.isStatus());
        Mockito.verify(shelfRepository).save(shelf);
    }

    @Test
    void findAllActiveTest() {
        Shelf shelf = new Shelf();
        shelf.setIdentifier("S1");
        shelf.setStatus(true);

        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setIdentifier("S1");

        Mockito.when(shelfRepository.findByStatusTrue())
                .thenReturn(List.of(shelf));
        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(shelfDto);

        List<ShelfDto> response = shelfService.findAllActive();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("S1", response.get(0).getIdentifier());
    }

    @Test
    void toggleStatusShelfNotFoundTest() {

        Mockito.when(shelfRepository.findByIdentifier("S1"))
                .thenReturn(null);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> shelfService.toggleStatus("S1")
        );

        Assertions.assertEquals("Shelf not found", ex.getMessage());
    }

    @Test
    void findByIdentifierNullTest() {

        Mockito.when(shelfRepository.findByIdentifier("S1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, ShelfDto.class))
                .thenReturn(null);

        ShelfDto response = shelfService.findByIdentifier("S1");

        Assertions.assertNull(response);
    }

    @Test
    void save_mapperAndRepositoryInvocationTest() {

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S2");

        Shelf shelf = new Shelf();
        shelf.setIdentifier("S2");

        Mockito.when(shelfRepository.findByIdentifier("S2"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(dto, Shelf.class))
                .thenReturn(shelf);
        Mockito.when(shelfRepository.save(shelf))
                .thenReturn(shelf);

        shelfService.save(dto);

        Mockito.verify(modelMapper).map(dto, Shelf.class);
        Mockito.verify(shelfRepository).save(shelf);
    }

    @Test
    void save_existingShelf_doesNotSave() {

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S3");

        Mockito.when(shelfRepository.findByIdentifier("S3"))
                .thenReturn(new Shelf());

        shelfService.save(dto);

        Mockito.verify(shelfRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void update_mapperMapsOntoExistingShelf() {

        Shelf existing = new Shelf();
        existing.setIdentifier("S4");

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S4");

        Mockito.when(shelfRepository.findByIdentifier("S4"))
                .thenReturn(existing);
        Mockito.when(shelfRepository.save(existing))
                .thenReturn(existing);

        shelfService.update(dto);

        Mockito.verify(modelMapper).map(dto, existing);
        Mockito.verify(shelfRepository).save(existing);
    }

    @Test
    void findByIdentifier_mapperInvocationTest() {

        Shelf shelf = new Shelf();
        shelf.setIdentifier("S5");

        ShelfDto dto = new ShelfDto();
        dto.setIdentifier("S5");

        Mockito.when(shelfRepository.findByIdentifier("S5"))
                .thenReturn(shelf);
        Mockito.when(modelMapper.map(shelf, ShelfDto.class))
                .thenReturn(dto);

        ShelfDto response = shelfService.findByIdentifier("S5");

        Mockito.verify(modelMapper).map(shelf, ShelfDto.class);
        Assertions.assertEquals("S5", response.getIdentifier());
    }

    @Test
    void findAll_mapperInvocationTest() {

        Pageable pageable = PageRequest.of(0, 10);
        List<Shelf> shelves = List.of(new Shelf());

        Page<Shelf> page =
                new PageImpl<>(shelves, pageable, shelves.size());

        Mockito.when(shelfRepository.findAll(pageable))
                .thenReturn(page);
        Mockito.when(modelMapper.map(
                Mockito.eq(shelves),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of(new ShelfDto()));

        shelfService.findAll(pageable);

        Mockito.verify(modelMapper).map(
                Mockito.eq(shelves),
                Mockito.any(java.lang.reflect.Type.class)
        );
    }

    @Test
    void findAllActive_emptyResultTest() {

        Mockito.when(shelfRepository.findByStatusTrue())
                .thenReturn(List.of());

        List<ShelfDto> response = shelfService.findAllActive();

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());

        Mockito.verify(shelfRepository)
                .findByStatusTrue();

        Mockito.verify(modelMapper, Mockito.never())
                .map(Mockito.any(Shelf.class), Mockito.eq(ShelfDto.class));
    }

    @Test
    void toggleStatus_saveAlwaysCalled() {

        Shelf shelf = new Shelf();
        shelf.setIdentifier("S6");
        shelf.setStatus(false); // initial state

        Mockito.when(shelfRepository.findByIdentifier("S6"))
                .thenReturn(shelf);

        shelfService.toggleStatus("S6");

        Assertions.assertTrue(shelf.isStatus(), "Status should be toggled to true");

        Mockito.verify(shelfRepository)
                .findByIdentifier("S6");

        Mockito.verify(shelfRepository)
                .save(shelf);

        Mockito.verifyNoMoreInteractions(shelfRepository);
    }
}