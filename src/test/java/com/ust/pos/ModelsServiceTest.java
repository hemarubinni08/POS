package com.ust.pos;

import com.ust.pos.models.service.impl.ModelsServiceImpl;
import com.ust.pos.dto.ModelsDto;
import com.ust.pos.model.Models;
import com.ust.pos.model.ModelsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ModelsServiceTest {

    @InjectMocks
    private ModelsServiceImpl modelsService;

    @Mock
    private ModelsRepository modelsRepository;

    //  SAVE
    @Test
    void saveTest_Success() {

        ModelsDto dto = new ModelsDto();
        dto.setModelName("iPhone");

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(null);

        Mockito.when(modelsRepository.save(Mockito.any(Models.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ModelsDto response = modelsService.save(dto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("iPhone", response.getIdentifier());
    }

    @Test
    void saveTest_Failure_EmptyName() {

        ModelsDto dto = new ModelsDto();
        dto.setModelName("");

        ModelsDto response = modelsService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Model name is required", response.getMessage());
    }

    @Test
    void saveTest_Failure_AlreadyExists() {

        ModelsDto dto = new ModelsDto();
        dto.setModelName("iPhone");

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(new Models());

        ModelsDto response = modelsService.save(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Model already exists", response.getMessage());
    }

    //  FIND BY IDENTIFIER
    @Test
    void findByIdentifierTest() {

        Models model = new Models();
        model.setIdentifier("iPhone");
        model.setModelName("iPhone");

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(model);

        ModelsDto response = modelsService.findByIdentifier("iPhone");

        Assertions.assertNotNull(response);
        Assertions.assertEquals("iPhone", response.getIdentifier());
    }

    @Test
    void findByIdentifier_NotFound() {

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(null);

        ModelsDto response = modelsService.findByIdentifier("iPhone");

        Assertions.assertNull(response);
    }

    //  UPDATE
    @Test
    void updateTest_Success() {

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone");
        dto.setStatus(true);

        Models model = new Models();
        model.setIdentifier("iPhone");

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(model);

        Mockito.when(modelsRepository.save(model))
                .thenReturn(model);

        ModelsDto response = modelsService.update(dto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTest_Failure() {

        ModelsDto dto = new ModelsDto();
        dto.setIdentifier("iPhone");

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(null);

        ModelsDto response = modelsService.update(dto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Model not found", response.getMessage());
    }

    //  DELETE
    @Test
    void deleteTest() {

        Mockito.doNothing()
                .when(modelsRepository)
                .deleteByIdentifier("iPhone");

        modelsService.delete("iPhone");

        Mockito.verify(modelsRepository).deleteByIdentifier("iPhone");
    }

    //  FIND ALL
    @Test
    void findAllTest() {

        Models model = new Models();
        model.setIdentifier("iPhone");
        model.setModelName("iPhone");
        model.setStatus(true);

        Mockito.when(modelsRepository.findAll())
                .thenReturn(List.of(model));

        List<ModelsDto> result = modelsService.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("iPhone", result.get(0).getIdentifier());
    }

    //  ACTIVE
    @Test
    void findActiveModelsTest() {

        Models active = new Models();
        active.setIdentifier("A");
        active.setStatus(true);

        Models inactive = new Models();
        inactive.setIdentifier("B");
        inactive.setStatus(false);

        Mockito.when(modelsRepository.findAll())
                .thenReturn(List.of(active, inactive));

        List<ModelsDto> result = modelsService.findActiveModels();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("A", result.get(0).getIdentifier());
    }

    //  TOGGLE
    @Test
    void toggleStatusTest() {

        Models model = new Models();
        model.setIdentifier("iPhone");
        model.setStatus(true);

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(model);

        Mockito.when(modelsRepository.save(model))
                .thenReturn(model);

        ModelsDto response = modelsService.toggleStatus("iPhone");

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertFalse(response.getStatus());
    }

    @Test
    void toggleStatus_NotFound() {

        Mockito.when(modelsRepository.findByIdentifier("iPhone"))
                .thenReturn(null);

        ModelsDto response = modelsService.toggleStatus("iPhone");

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Model not found", response.getMessage());
    }
}