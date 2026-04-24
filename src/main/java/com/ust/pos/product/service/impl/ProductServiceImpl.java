package com.ust.pos.product.service.impl;

import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Category;
import com.ust.pos.model.CategoryRepository;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String ACTIVE = "ACTIVE";

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        if (productDto.getCategoryIdentifiers() == null || productDto.getCategoryIdentifiers().isEmpty()) {

            productDto.setSuccess(false);
            productDto.setMessage("Select at least one category");
            return productDto;
        }

        for (String categoryIdentifier : productDto.getCategoryIdentifiers()) {
            if (!categoryRepository.existsByIdentifier(categoryIdentifier)) {
                productDto.setSuccess(false);
                productDto.setMessage("Invalid category selected");
                return productDto;
            }
        }

        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);

        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());

        if (optionalProduct.isEmpty()) {
            productDto.setSuccess(false);
            productDto.setMessage("Product not found");
            return productDto;
        }

        Product existingProduct = optionalProduct.get();

        if (!existingProduct.getSku().equals(productDto.getSku()) && productRepository.existsByIdentifier(productDto.getIdentifier())) {

            productDto.setSuccess(false);
            productDto.setMessage("Product with identifier already exists");
            return productDto;
        }

        if (!existingProduct.getIdentifier().equals(productDto.getIdentifier()) && productRepository.existsByIdentifier(productDto.getIdentifier())) {

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

        return productRepository.findAll().stream().map(product -> {

            ProductDto dto = modelMapper.map(product, ProductDto.class);

            List<String> names = product.getCategoryIdentifiers().stream().map(id -> categoryRepository.findByIdentifier(id).map(Category::getIdentifier).orElse("UNKNOWN")).toList();

            dto.setCategoryNames(names);

            return dto;
        }).toList();
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