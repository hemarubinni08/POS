package com.ust.pos;

import com.ust.pos.dto.ModelDto;
import com.ust.pos.model.service.impl.ModelServiceImpl;
import com.ust.pos.modell.Model;
import com.ust.pos.modell.ModelRepository;
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
 class ModelServiceTest {

    @InjectMocks
    private ModelServiceImpl modelService;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveTest() {
        ModelDto modelDto = new ModelDto();
        modelDto.setIdentifier("M1");

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(null);

        Model model = new Model();
        model.setIdentifier("M1");

        Mockito.when(modelMapper.map(modelDto, Model.class))
                .thenReturn(model);
        Mockito.when(modelRepository.save(model))
                .thenReturn(model);

        ModelDto response = modelService.save(modelDto);

        Assertions.assertEquals("M1", response.getIdentifier());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void saveTestFailure() {
        ModelDto modelDto = new ModelDto();
        modelDto.setIdentifier("M1");

        Model existingModel = new Model();
        existingModel.setIdentifier("M1");

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(existingModel);

        ModelDto response = modelService.save(modelDto);

        Assertions.assertEquals("M1", response.getIdentifier());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void findByIdentifierTest() {
        Model model = new Model();
        model.setIdentifier("M1");

        ModelDto modelDto = new ModelDto();
        modelDto.setIdentifier("M1");

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(model);
        Mockito.when(modelMapper.map(model, ModelDto.class))
                .thenReturn(modelDto);

        ModelDto response = modelService.findByIdentifier("M1");

        Assertions.assertEquals("M1", response.getIdentifier());
    }

    @Test
    void updateTest() {
        ModelDto modelDto = new ModelDto();
        modelDto.setIdentifier("M1");

        Model existingModel = new Model();
        existingModel.setIdentifier("M1");

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(existingModel);
        Mockito.when(modelRepository.save(existingModel))
                .thenReturn(existingModel);

        ModelDto response = modelService.update(modelDto);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    void updateTestFailure() {
        ModelDto modelDto = new ModelDto();
        modelDto.setIdentifier("M1");

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(null);

        ModelDto response = modelService.update(modelDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNotNull(response.getMessage());
    }

    @Test
    void deleteTest() {
        Mockito.doNothing()
                .when(modelRepository)
                .deleteByIdentifier("M1");

        modelService.delete("M1");

        Mockito.verify(modelRepository)
                .deleteByIdentifier("M1");
    }

    @Test
    void findAllTest() {
        Model model = new Model();
        model.setIdentifier("M1");

        ModelDto modelDto = new ModelDto();
        modelDto.setIdentifier("M1");

        List<Model> models = List.of(model);
        List<ModelDto> modelDtos = List.of(modelDto);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(new ArrayList<>()));
        Page<Model> modelPage =
                new PageImpl<>(models, pageable, models.size());

        Mockito.when(modelRepository.findAll(pageable))
                .thenReturn(modelPage);

        Mockito.when(modelMapper.map(
                Mockito.eq(models),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(modelDtos);

        List<ModelDto> response = modelService.findAll(pageable);

        Assertions.assertEquals(1, response.size());
    }

    @Test
    void toggleStatusTest() {
        Model model = new Model();
        model.setIdentifier("M1");
        model.setStatus(true);

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(model);

        modelService.toggleStatus("M1");

        Assertions.assertFalse(model.isStatus());
        Mockito.verify(modelRepository).save(model);
    }

    @Test
    void toggleStatusModelNotFoundTest() {

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(null);

        RuntimeException ex = Assertions.assertThrows(
                RuntimeException.class,
                () -> modelService.toggleStatus("M1")
        );

        Assertions.assertEquals("model not found", ex.getMessage());
    }

    @Test
    void findByIdentifierNullTest() {

        Mockito.when(modelRepository.findByIdentifier("M1"))
                .thenReturn(null);

        Mockito.when(modelMapper.map(null, ModelDto.class))
                .thenReturn(null);

        ModelDto response = modelService.findByIdentifier("M1");

        Assertions.assertNull(response);
    }

    @Test
    void save_mapperAndRepositoryInvocationTest() {

        ModelDto dto = new ModelDto();
        dto.setIdentifier("M2");

        Model model = new Model();
        model.setIdentifier("M2");

        Mockito.when(modelRepository.findByIdentifier("M2"))
                .thenReturn(null);
        Mockito.when(modelMapper.map(dto, Model.class))
                .thenReturn(model);
        Mockito.when(modelRepository.save(model))
                .thenReturn(model);

        modelService.save(dto);

        Mockito.verify(modelMapper).map(dto, Model.class);
        Mockito.verify(modelRepository).save(model);
    }

    @Test
    void save_existingModel_doesNotSave() {

        ModelDto dto = new ModelDto();
        dto.setIdentifier("M3");

        Mockito.when(modelRepository.findByIdentifier("M3"))
                .thenReturn(new Model());

        modelService.save(dto);

        Mockito.verify(modelRepository, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void update_mapperMapsOntoExistingEntity() {

        Model existing = new Model();
        existing.setIdentifier("M4");

        ModelDto dto = new ModelDto();
        dto.setIdentifier("M4");

        Mockito.when(modelRepository.findByIdentifier("M4"))
                .thenReturn(existing);
        Mockito.when(modelRepository.save(existing))
                .thenReturn(existing);

        modelService.update(dto);

        Mockito.verify(modelMapper).map(dto, existing);
        Mockito.verify(modelRepository).save(existing);
    }

    @Test
    void findByIdentifier_mapperInvocationTest() {

        Model model = new Model();
        model.setIdentifier("M5");

        ModelDto dto = new ModelDto();
        dto.setIdentifier("M5");

        Mockito.when(modelRepository.findByIdentifier("M5"))
                .thenReturn(model);
        Mockito.when(modelMapper.map(model, ModelDto.class))
                .thenReturn(dto);

        ModelDto response = modelService.findByIdentifier("M5");

        Mockito.verify(modelMapper).map(model, ModelDto.class);
        Assertions.assertEquals("M5", response.getIdentifier());
    }

    @Test
    void findAll_mapperInvocationTest() {

        Pageable pageable = PageRequest.of(0, 10);
        List<Model> models = List.of(new Model());

        Page<Model> page =
                new PageImpl<>(models, pageable, models.size());

        Mockito.when(modelRepository.findAll(pageable))
                .thenReturn(page);

        Mockito.when(modelMapper.map(
                Mockito.eq(models),
                Mockito.any(java.lang.reflect.Type.class)
        )).thenReturn(List.of(new ModelDto()));

        modelService.findAll(pageable);

        Mockito.verify(modelMapper).map(
                Mockito.eq(models),
                Mockito.any(java.lang.reflect.Type.class)
        );
    }

    @Test
    void toggleStatus_saveAlwaysCalled() {

        Model model = new Model();
        model.setIdentifier("M6");
        model.setStatus(false);

        Mockito.when(modelRepository.findByIdentifier("M6"))
                .thenReturn(model);

        modelService.toggleStatus("M6");

        Mockito.verify(modelRepository).save(model);
        Assertions.assertTrue(model.isStatus());
    }
}