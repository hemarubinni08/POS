package com.ust.pos.product.service.impl;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<String> buildCategoryDisplayNames(List<String> categoryIdentifiers) {

        if (categoryIdentifiers == null) {
            return List.of();
        }

        return categoryIdentifiers.stream()
                .map(val -> val.replace("|", " (") + ")")
                .toList();
    }

    @Override
    public List<ProductDto> getAllProducts() {

        return productRepository.findAll().stream().map(product -> {

            ProductDto dto = modelMapper.map(product, ProductDto.class);

            dto.setCategoryNames(
                    buildCategoryDisplayNames(product.getCategoryIdentifiers())
            );

            return dto;

        }).toList();
    }


    @Override
    public ProductDto getProduct(Long id) {

        ProductDto dto = new ProductDto();

        productRepository.findById(id).ifPresentOrElse(product -> {

            modelMapper.map(product, dto);

            dto.setCategoryNames(
                    buildCategoryDisplayNames(product.getCategoryIdentifiers())
            );

            dto.setSuccess(true);

        }, () -> {
            dto.setSuccess(false);
            dto.setMessage("Product not found");
        });

        return dto;
    }


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
        return productDto;
    }

    @Override
    public boolean deleteProduct(Long id) {
        productRepository.deleteById(id);
        return true;
    }
}
