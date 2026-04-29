package com.ust.pos.modelProduct.service;

import com.ust.pos.dto.ModelProductDto;
import org.springframework.ui.Model;

import java.util.List;

public interface ModelProductService {
    ModelProductDto save(ModelProductDto modelProductDto);

    ModelProductDto update(ModelProductDto modelProductDto);

    ModelProductDto findByIdentifier(String identifier);

    List<ModelProductDto> findAll();

    void delete(String identifier);
    void toggleStatus(String identifier);
}
