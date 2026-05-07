package com.ust.pos.product.service.impl;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.ProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String ACTIVE = "ACTIVE";
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ===== SAVE =====
    @Override
    public ProductDto save(ProductDto productDto) {

        if (productDto.getIdentifier() == null || productDto.getIdentifier().isEmpty()) {
            productDto.setSuccess(false);
            productDto.setMessage("Identifier required");
            return productDto;
        }

        Product existing = productRepository.findByIdentifier(productDto.getIdentifier());


        if (existing != null) {
            productDto.setSuccess(false);
            productDto.setMessage("Product already exists");
            return productDto;
        }

        Product product = modelMapper.map(productDto, Product.class);

        productRepository.save(product);

        productDto.setSuccess(true);
        return productDto;
    }

    // ===== UPDATE =====
    @Override
    public ProductDto update(ProductDto productDto) {

        Product existing = productRepository.findByIdentifier(productDto.getIdentifier());

        if (existing == null) {
            productDto.setSuccess(false);
            productDto.setMessage(PRODUCT_NOT_FOUND);
            return productDto;
        }

        modelMapper.map(productDto, existing);

        productRepository.save(existing);

        productDto.setSuccess(true);
        return productDto;
    }

    // ===== FIND BY ID =====
    @Override
    public ProductDto findByIdentifier(String identifier) {

        Product product = productRepository.findByIdentifier(identifier);

        if (product == null) {
            ProductDto dto = new ProductDto();
            dto.setSuccess(false);
            dto.setMessage(PRODUCT_NOT_FOUND);
            return dto;
        }

        ProductDto dto = modelMapper.map(product, ProductDto.class);

        dto.setSuccess(true);
        return dto;
    }

    // ===== FIND ALL =====
    @Override
    public List<ProductDto> findAll() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> result = new ArrayList<>();

        for (Product product : products) {

            ProductDto dto = modelMapper.map(product, ProductDto.class);


            result.add(dto);
        }

        return result;
    }

    // ===== DELETE =====
    @Override
    @Transactional
    public void delete(String identifier) {
        productRepository.deleteByIdentifier(identifier);
    }

    // ===== TOGGLE =====
    @Override
    public ProductDto toggleStatus(String identifier) {

        ProductDto response = new ProductDto();

        Product product = productRepository.findByIdentifier(identifier);

        if (product == null) {
            response.setSuccess(false);
            response.setMessage(PRODUCT_NOT_FOUND);
            return response;
        }

        product.setStatus(
                ACTIVE.equals(product.getStatus()) ? "INACTIVE" : ACTIVE
        );

        Product saved = productRepository.save(product);

        response.setIdentifier(saved.getIdentifier());
        response.setStatus(saved.getStatus());

        response.setSuccess(true);
        response.setMessage("Status updated successfully");

        return response;
    }

    // ===== ACTIVE PRODUCTS =====
    @Override
    public List<ProductDto> findActiveProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> result = new ArrayList<>();

        for (Product product : products) {

            if (!ACTIVE.equals(product.getStatus())) continue;

            ProductDto dto = modelMapper.map(product, ProductDto.class);

            result.add(dto);
        }

        return result;
    }
}