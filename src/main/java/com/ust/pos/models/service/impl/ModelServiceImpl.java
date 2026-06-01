package com.ust.pos.models.service.impl;

import com.ust.pos.dto.ModelDto;
import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Model;
import com.ust.pos.model.ModelRepository;
import com.ust.pos.model.Product;
import com.ust.pos.models.service.ModelService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ModelDto save(ModelDto modelDto) {
        Model existingModel = modelRepository.findByIdentifier(modelDto.getIdentifier());
        if (existingModel != null) {
            modelDto.setMessage("Model with identifier - " + modelDto.getIdentifier() + " already exists");
            modelDto.setSuccess(false);
            return modelDto;
        }
        Model model = modelMapper.map(modelDto, Model.class);
        modelRepository.save(model);
        return modelDto;
    }

    @Override
    public PaginationResponseDto<ModelDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<ModelDto>>() {
        }.getType();
        Page<Model> modelPage = modelRepository.findAll(pageable);

        PaginationResponseDto<ModelDto> modelPaginationResponseDto = new PaginationResponseDto<>();
        modelPaginationResponseDto.setDtoList(modelMapper.map(modelPage.getContent(), listType));
        modelPaginationResponseDto.setTotalRecords(modelPage.getTotalElements());
        modelPaginationResponseDto.setTotalPages(modelPage.getTotalPages());
        modelPaginationResponseDto.setSizePerPage(pageable.getPageSize());
        modelPaginationResponseDto.setPage(pageable.getPageNumber());

        return modelPaginationResponseDto;
    }

    @Override
    public ModelDto findByIdentifier(String identifier) {
        return modelMapper.map(modelRepository.findByIdentifier(identifier), ModelDto.class);
    }

    @Transactional
    @Override
    public void deleteByIdentifier(String identifier) {
        modelRepository.deleteByIdentifier(identifier);
    }

    @Override
    public ModelDto update(ModelDto modelDto) {
        Model existingModel = modelRepository.findByIdentifier(modelDto.getIdentifier());
        if (existingModel == null) {
            modelDto.setMessage("Model with identifier - " + modelDto.getIdentifier() + "not found");
            modelDto.setSuccess(false);
            return modelDto;
        }
        modelMapper.map(modelDto, existingModel);
        modelRepository.save(existingModel);
        return modelDto;
    }

    @Override
    @Transactional
    public ModelDto toggleStatus(String identifier, boolean status) {
        ModelDto response = new ModelDto();
        Model model = modelRepository.findByIdentifier(identifier);
        if (model == null) {
            response.setSuccess(false);
            response.setMessage("Model not found");
            return response;
        }
        model.setStatus(status);
        response.setSuccess(true);
        response.setMessage("Status updated successfully");
        return response;
    }
}
