package com.ust.pos;

import com.ust.pos.dto.ModelsDto;
import com.ust.pos.model.Models;
import com.ust.pos.model.ModelsRepository;
import com.ust.pos.models.service.impl.ModelsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Type;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ModelsServiceTest {

    @InjectMocks
    private ModelsServiceImpl modelsService;

    @Mock
    private ModelsRepository modelsRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void findByIdentifierTest() {

        Models models = new Models();
        models.setIdentifier("M1");

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("M1");

        Mockito.when(modelsRepository.findByIdentifier("M1"))
                .thenReturn(models);

        Mockito.when(modelMapper.map(models, ModelsDto.class))
                .thenReturn(dto);

        ModelsDto response = modelsService.findByIdentifier("M1");

        Assertions.assertEquals("M1",
                response.getIdentifier());

        Mockito.verify(modelsRepository)
                .findByIdentifier("M1");
    }

    @Test
    void toggleStatusTest() {

        Models models = new Models();
        models.setIdentifier("M1");
        models.setStatus(true);

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("M1");
        dto.setStatus(false);

        Mockito.when(modelsRepository.findByIdentifier("M1"))
                .thenReturn(models);

        Mockito.when(modelsRepository.save(models))
                .thenReturn(models);

        Mockito.when(modelMapper.map(models, ModelsDto.class))
                .thenReturn(dto);

        ModelsDto response = modelsService.toggleStatus("M1");

        Assertions.assertEquals("M1",
                response.getIdentifier());

        Mockito.verify(modelsRepository)
                .save(models);
    }

    @Test
    void saveTest() {

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier(" M1 ");

        Models models = new Models();

        Mockito.when(modelsRepository.findByIdentifier("M1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(
                        Mockito.any(ModelsDto.class),
                        Mockito.eq(Models.class)))
                .thenReturn(models);

        ModelsDto response = modelsService.save(dto);

        Assertions.assertEquals("M1",
                response.getIdentifier());

        Mockito.verify(modelsRepository)
                .save(models);
    }

    @Test
    void saveDuplicateTest() {

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("M1");

        Models existing = new Models();

        Mockito.when(modelsRepository.findByIdentifier("M1"))
                .thenReturn(existing);

        ModelsDto response = modelsService.save(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals(
                "Models with identifier - M1 already exists",
                response.getMessage()
        );
    }

    @Test
    void updateTest() {

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("M1");

        Models existing = new Models();
        existing.setIdentifier("M1");

        Mockito.when(modelsRepository.findByIdentifier("M1"))
                .thenReturn(existing);

        Mockito.when(modelsRepository.save(existing))
                .thenReturn(existing);

        Mockito.doNothing()
                .when(modelMapper)
                .map(dto, existing);

        ModelsDto response = modelsService.update(dto);

        Assertions.assertEquals("M1",
                response.getIdentifier());

        Mockito.verify(modelsRepository)
                .save(existing);
    }

    @Test
    void updateNotFoundTest() {

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("M1");

        Mockito.when(modelsRepository.findByIdentifier("M1"))
                .thenReturn(null);

        ModelsDto response = modelsService.update(dto);

        Assertions.assertFalse(response.isSuccess());

        Assertions.assertEquals(
                "Models with identifier - M1 not found",
                response.getMessage()
        );
    }

    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(modelsRepository)
                .deleteByIdentifier("M1");

        boolean response = modelsService.delete("M1");

        Assertions.assertTrue(response);

        Mockito.verify(modelsRepository)
                .deleteByIdentifier("M1");
    }

    @Test
    void findAllTest() {

        Pageable pageable = PageRequest.of(0, 10);

        Models models = new Models();
        models.setIdentifier("M1");

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("M1");

        List<Models> modelsList = List.of(models);
        List<ModelsDto> dtoList = List.of(dto);

        Page<Models> modelsPage = new PageImpl<>(modelsList);

        Mockito.when(modelsRepository.findAll(pageable))
                .thenReturn(modelsPage);

        Mockito.when(modelMapper.map(
                        Mockito.eq(modelsList),
                        Mockito.any(Type.class)))
                .thenReturn(dtoList);

        List<ModelsDto> response =
                modelsService.findAll(pageable);

        Assertions.assertEquals(1, response.size());

        Mockito.verify(modelsRepository)
                .findAll(pageable);
    }

    @Test
    void findIfTrueTest() {

        Models models = new Models();
        models.setIdentifier("M1");
        models.setStatus(true);

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("M1");
        dto.setStatus(true);

        List<Models> modelsList = List.of(models);
        List<ModelsDto> dtoList = List.of(dto);

        Mockito.when(modelsRepository.findByStatusIsTrue())
                .thenReturn(modelsList);

        Mockito.when(modelMapper.map(
                        Mockito.eq(modelsList),
                        Mockito.any(Type.class)))
                .thenReturn(dtoList);

        List<ModelsDto> response = modelsService.findIfTrue();

        Assertions.assertEquals(1, response.size());

        Mockito.verify(modelsRepository)
                .findByStatusIsTrue();
    }
}