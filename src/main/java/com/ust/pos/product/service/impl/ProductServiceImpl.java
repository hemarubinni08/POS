package com.ust.pos.product.service.impl;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String ACTIVE = "ACTIVE";

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        if (productRepository.existsBySku(productDto.getSku())) {
            productDto.setSuccess(false);
            productDto.setMessage("Product with SKU already exists");
            return productDto;
        }

        if (productRepository.existsByName(productDto.getName())) {
            productDto.setSuccess(false);
            productDto.setMessage("Product with name already exists");
            return productDto;
        }

        // default status
        if (productDto.getStatus() == null) {
            productDto.setStatus(ACTIVE);
        }

        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);

        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        Optional<Product> optionalProduct =
                productRepository.findById(productDto.getId());

        if (optionalProduct.isEmpty()) {
            productDto.setSuccess(false);
            productDto.setMessage("Product not found");
            return productDto;
        }

        Product existingProduct = optionalProduct.get();

        // SKU duplicate check (excluding current product)
        if (!existingProduct.getSku().equals(productDto.getSku())
                && productRepository.existsBySku(productDto.getSku())) {

            productDto.setSuccess(false);
            productDto.setMessage("Product with SKU already exists");
            return productDto;
        }

        // NAME duplicate check (excluding current product)
        if (!existingProduct.getName().equals(productDto.getName())
                && productRepository.existsByName(productDto.getName())) {

            productDto.setSuccess(false);
            productDto.setMessage("Product with name already exists");
            return productDto;
        }

        modelMapper.map(productDto, existingProduct);
        productRepository.save(existingProduct);

        return productDto;
    }

    @Override
    public ProductDto getProduct(Long id) {

        ProductDto dto = new ProductDto();

        productRepository.findById(id).ifPresentOrElse(product -> {
            modelMapper.map(product, dto);
        }, () -> {
            dto.setSuccess(false);
            dto.setMessage("Product not found");
        });

        return dto;
    }

    @Override
    public List<ProductDto> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }
}