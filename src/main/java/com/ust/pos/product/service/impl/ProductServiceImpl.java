package com.ust.pos.product.service.impl;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.ProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto findByIdentifier(String identifier) {

        Optional<Product> product = productRepository.findByIdentifier(identifier);

        if (product.isEmpty()) {
            ProductDto dto = new ProductDto();
            dto.setMessage("Product with identifier - " + identifier + " not found");
            dto.setSuccess(false);
            return dto;
        }

        return modelMapper.map(product.get(), ProductDto.class);
    }

    @Override
    public ProductDto save(ProductDto productDto) {

        String identifier = productDto.getIdentifier();

        if (identifier == null || identifier.isEmpty()) {
            productDto.setMessage("Identifier is required");
            productDto.setSuccess(false);
            return productDto;
        }

        Optional<Product> existingProduct = productRepository.findByIdentifier(identifier);

        if (existingProduct.isPresent()) {
            productDto.setMessage("Product with identifier - " + identifier + " already exists");
            productDto.setSuccess(false);
            return productDto;
        }

        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);

        return productDto;
    }

    @Override
    public ProductDto update(ProductDto productDto) {

        String identifier = productDto.getIdentifier();

        if (identifier == null || identifier.isEmpty()) {
            productDto.setMessage("Invalid identifier");
            productDto.setSuccess(false);
            return productDto;
        }

        Optional<Product> existingProduct = productRepository.findByIdentifier(identifier);

        if (existingProduct.isEmpty()) {
            productDto.setMessage("Product with identifier - " + identifier + " not found");
            productDto.setSuccess(false);
            return productDto;
        }

        Product product = existingProduct.get();
        modelMapper.map(productDto, product);
        productRepository.save(product);

        return productDto;
    }

    @Override
    @Transactional
    public void delete(String identifier) {
        productRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<ProductDto> findAll() {
        Type listType = new TypeToken<List<ProductDto>>() {}.getType();
        return modelMapper.map(productRepository.findAll(), listType);
    }
}