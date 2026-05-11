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
import org.springframework.data.domain.*;
import java.util.ArrayList;
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
    void save_Success() {
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone15");
        Mockito.when(modelsRepository.findByIdentifier("iPhone15"))
                .thenReturn(null);
        Models model = new Models();
        Mockito.when(modelMapper.map(dto, Models.class))
                .thenReturn(model);
        Mockito.when(modelsRepository.save(model))
                .thenReturn(model);
        ModelsDto response = modelsService.save(dto);
        Mockito.verify(modelMapper).map(dto, Models.class);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Model created successfully", response.getMessage());
    }

    @Test
    void save_Failure() {
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone15");
        Mockito.when(modelsRepository.findByIdentifier("iPhone15"))
                .thenReturn(new Models());
        ModelsDto response = modelsService.save(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifier_Success() {
        Models model = new Models();
        model.setIdentifier("iPhone15");
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone15");
        Mockito.when(modelsRepository.findByIdentifier("iPhone15"))
                .thenReturn(model);
        Mockito.when(modelMapper.map(model, ModelsDto.class))
                .thenReturn(dto);
        ModelsDto response = modelsService.findByIdentifier("iPhone15");
        Assertions.assertEquals("iPhone15", response.getIdentifier());
    }

    @Test
    void findByIdentifier_Null() {
        Mockito.when(modelsRepository.findByIdentifier("X"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(null, ModelsDto.class))
                .thenReturn(null);
        ModelsDto response = modelsService.findByIdentifier("X");
        Assertions.assertNull(response);
    }

    @Test
    void update_Success() {
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone15");
        Models existing = new Models();
        existing.setIdentifier("iPhone15");
        Mockito.when(modelsRepository.findByIdentifier("iPhone15"))
                .thenReturn(existing);
        Mockito.when(modelsRepository.save(existing))
                .thenReturn(existing);
        ModelsDto response = modelsService.update(dto);
        Mockito.verify(modelMapper).map(dto, existing);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Model updated successfully", response.getMessage());
    }

    @Test
    void update_Failure() {
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone15");
        Mockito.when(modelsRepository.findByIdentifier("iPhone15"))
                .thenReturn(null);
        ModelsDto response = modelsService.update(dto);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void delete_Test() {
        Mockito.doNothing()
                .when(modelsRepository)
                .deleteByIdentifier("iPhone15");
        boolean result = modelsService.delete("iPhone15");
        Mockito.verify(modelsRepository).deleteByIdentifier("iPhone15");
        Assertions.assertTrue(result);
    }

    @Test
    void findAll_WithData() {
        Models model = new Models();
        model.setIdentifier("iPhone15");
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone15");
        List<Models> models = List.of(model);
        List<ModelsDto> modelDtos = List.of(dto);
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Models> modelsPage =
                new PageImpl<>(models, pageable, models.size());
        Mockito.when(modelsRepository.findAll(pageable))
                .thenReturn(modelsPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(models),
                        Mockito.any(java.lang.reflect.Type.class)
                )
        ).thenReturn(modelDtos);
        List<ModelsDto> response = modelsService.findAll(pageable);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals("iPhone15", response.get(0).getIdentifier());
    }

    @Test
    void findAll_Empty() {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(new ArrayList<>()));
        Page<Models> modelsPage =
                new PageImpl<>(List.of(), pageable, 0);
        Mockito.when(modelsRepository.findAll(pageable))
                .thenReturn(modelsPage);
        Mockito.when(
                modelMapper.map(
                        Mockito.eq(List.of()),
                        Mockito.any(java.lang.reflect.Type.class)
                )
        ).thenReturn(List.of());
        List<ModelsDto> response = modelsService.findAll(pageable);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void toggleStatus_TrueToFalse() {
        Models model = new Models();
        model.setIdentifier("iPhone15");
        model.setStatus(true);
        Models updated = new Models();
        updated.setIdentifier("iPhone15");
        updated.setStatus(false);
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone15");
        dto.setStatus(false);
        Mockito.when(modelsRepository.findByIdentifier("iPhone15"))
                .thenReturn(model);
        Mockito.when(modelsRepository.save(model))
                .thenReturn(updated);
        Mockito.when(modelMapper.map(updated, ModelsDto.class))
                .thenReturn(dto);
        ModelsDto response = modelsService.toggleStatus("iPhone15");
        Assertions.assertFalse(response.isStatus());
    }

    @Test
    void toggleStatus_FalseToTrue() {
        Models model = new Models();
        model.setIdentifier("iPhone16");
        model.setStatus(false);
        Models updated = new Models();
        updated.setIdentifier("iPhone16");
        updated.setStatus(true);
        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone16");
        dto.setStatus(true);
        Mockito.when(modelsRepository.findByIdentifier("iPhone16"))
                .thenReturn(model);
        Mockito.when(modelsRepository.save(model))
                .thenReturn(updated);
        Mockito.when(modelMapper.map(updated, ModelsDto.class))
                .thenReturn(dto);
        ModelsDto response = modelsService.toggleStatus("iPhone16");
        Assertions.assertTrue(response.isStatus());
    }

    @Test
    void toggleStatus_Null_ShouldThrowException() {
        Mockito.when(modelsRepository.findByIdentifier("X"))
                .thenReturn(null);
        Assertions.assertThrows(
                NullPointerException.class,
                () -> modelsService.toggleStatus("X"));
    }
}