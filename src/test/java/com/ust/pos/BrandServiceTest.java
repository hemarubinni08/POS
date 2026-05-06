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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private ModelsRepository modelsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ModelsServiceImpl modelsService;

    /* ===================== SAVE ===================== */

    @Test
    void saveTest() {
        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setIdentifier("Admin");


        Mockito.when(modelsRepository.findByIdentifier("Admin")).thenReturn(null);
        Models models=new Models();
        Mockito.when(modelMapper.map(modelsDto, Models.class)).thenReturn(models);
        Mockito.when(modelsRepository.save(models)).thenReturn(models);

        ModelsDto response = modelsService.save(modelsDto);

        Assertions.assertEquals("Admin", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void saveTestFailure() {
        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setIdentifier("Admin");


        Models existingModels = new Models();
        existingModels.setIdentifier("Admin");


        Mockito.when(modelsRepository.findByIdentifier("Admin"))
                .thenReturn(existingModels);

        ModelsDto response = modelsService.save(modelsDto);

        Assertions.assertFalse(response.isSuccess());
    }

    /* ===================== FIND BY IDENTIFIER ===================== */

    @Test
    void findByIdentifierTest() {
        Models models = new Models();
        models.setIdentifier("Admin");

        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setIdentifier("Admin");

        Mockito.when(modelsRepository.findByIdentifier("Admin")).thenReturn(models);
        Mockito.when(modelMapper.map(models, ModelsDto.class)).thenReturn(modelsDto);

        ModelsDto response = modelsService.findByIdentifier("Admin");

        Assertions.assertEquals("Admin", response.getIdentifier());
    }

    /* ===================== UPDATE ===================== */

    @Test
    void updateTest() {
        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setIdentifier("Admin");

        Models existingModels = new Models();
        existingModels.setIdentifier("Admin");

        Mockito.when(modelsRepository.findByIdentifier("Admin"))
                .thenReturn(existingModels);
        Mockito.when(modelsRepository.save(existingModels))
                .thenReturn(existingModels);

        ModelsDto response = modelsService.update(modelsDto);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void updateTestFailure() {
        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setIdentifier("Admin");

        Mockito.when(modelsRepository.findByIdentifier("Admin"))
                .thenReturn(null);

        ModelsDto response = modelsService.update(modelsDto);

        Assertions.assertFalse(response.isSuccess());
    }

    /* ===================== DELETE ===================== */

    @Test
    void deleteTest() {
        Mockito.doNothing().when(modelsRepository)
                .deleteByIdentifier("Admin");

        modelsService.delete("Admin");


    }

    /* ===================== FIND ALL ===================== */

    @Test
    void findAllTest() {
        Models models = new Models();
        models.setIdentifier("Admin");

        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setIdentifier("Admin");

        List<Models> modelss = List.of(models);
        List<ModelsDto> modelsDtos = List.of(modelsDto);

        Mockito.when(modelsRepository.findAll()).thenReturn(modelss);
        Mockito.when(modelMapper.map(
                Mockito.eq(modelss),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(modelsDtos);

        List<ModelsDto> response = modelsService.findAll();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void findByStatusTest(){
        Models models = new Models();
        models.setIdentifier("Admin");
        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setIdentifier("Admin");

        List<Models> modelss = List.of(models);
        List<ModelsDto> modelsDtos = List.of(modelsDto);

        Mockito.when(modelsRepository.findByStatusIsTrue()).thenReturn(modelss);
        Mockito.when(modelMapper.map(
                Mockito.eq(modelss),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(modelsDtos);

        List<ModelsDto> response = modelsService.findIfTrue();

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleTestActive(){

        Models models = new Models();
        models.setStatus(false);
        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setStatus(true);
        Mockito.when(modelsRepository.findByIdentifier("Admin")).thenReturn(models);
        Mockito.when(modelMapper.map(models,ModelsDto.class)).thenReturn(modelsDto);
        ModelsDto response = modelsService.toggleStatus("Admin");
        Assertions.assertTrue(response.isStatus());

    }

    @Test
    void toggleTestInactive(){

        Models models = new Models();
        models.setStatus(true);
        ModelsDto modelsDto = new ModelsDto();
        modelsDto.setStatus(false);
        Mockito.when(modelsRepository.findByIdentifier("Admin")).thenReturn(models);
        Mockito.when(modelMapper.map(models,ModelsDto.class)).thenReturn(modelsDto);
        ModelsDto response = modelsService.toggleStatus("Admin");
        Assertions.assertFalse(response.isStatus());

    }
}
