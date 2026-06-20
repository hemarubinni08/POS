package com.ust.pos.product.service.impl;

import com.ust.pos.base.service.BaseService;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.product.service.ProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl extends BaseService implements ProductService {

    public static final String PRODUCT_NOT_FOUND = "Product not found";

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, PriceRepository priceRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        if (productDto.getIdentifier() == null || productDto.getIdentifier().trim().isEmpty()) {
            productDto.setSuccess(false);
            productDto.setMessage("Identifier required");
            return productDto;
        }
        Product product = productRepository.findByIdentifier(productDto.getIdentifier());
        if (product != null) {
            productDto.setSuccess(false);
            productDto.setMessage("Product already exists");
            return productDto;
        }
        Product saveProduct = modelMapper.map(productDto, Product.class);
        setCreatedDetails(saveProduct);
        Product savedProduct = productRepository.save(saveProduct);
        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);
        savedProductDto.setSuccess(true);
        savedProductDto.setMessage("Product saved successfully");
        return savedProductDto;
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product product = productRepository.findByIdentifier(productDto.getIdentifier());
        if (product == null) {
            productDto.setSuccess(false);
            productDto.setMessage(PRODUCT_NOT_FOUND);
            return productDto;
        }
        modelMapper.map(productDto, product);
        setModifiedDetails(product);
        Product updatedProduct = productRepository.save(product);
        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
        updatedProductDto.setSuccess(true);
        updatedProductDto.setMessage("Product updated successfully");
        return updatedProductDto;
    }

    @Override
    public ProductDto findByIdentifier(String identifier) {
        Product product = productRepository.findByIdentifier(identifier);
        if (product == null) {
            ProductDto productDto = new ProductDto();
            productDto.setSuccess(false);
            productDto.setMessage(PRODUCT_NOT_FOUND);
            return productDto;
        }
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setSuccess(true);
        return productDto;
    }

    @Override
    public WsDto<ProductDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<ProductDto>>() {
        }.getType();
        Page<Product> productPage = productRepository.findAll(pageable);

        WsDto<ProductDto> productWsDto = new WsDto<>();
        productWsDto.setDtoList(modelMapper.map(productPage.getContent(), listType));
        productWsDto.setTotalRecords(productPage.getTotalElements());
        productWsDto.setTotalPages(productPage.getTotalPages());
        productWsDto.setSizePerPage(pageable.getPageSize());
        productWsDto.setPage(pageable.getPageNumber());

        return productWsDto;
    }

    @Override
    public void delete(String identifier) {
        priceRepository.deleteByProductId(identifier);
        productRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<ProductDto> findActiveProducts() {
        List<Product> list = productRepository.findByStatusTrue();
        Type type = new TypeToken<List<ProductDto>>() {
        }.getType();
        return modelMapper.map(list, type);
    }

    @Override
    public ProductDto toggleStatus(String identifier) {
        Product product = productRepository.findByIdentifier(identifier);
        if (product == null) {
            ProductDto productDto = new ProductDto();
            productDto.setSuccess(false);
            productDto.setMessage(PRODUCT_NOT_FOUND);
            return productDto;
        }
        product.setStatus(!Boolean.TRUE.equals(product.getStatus()));
        setModifiedDetails(product);
        Product savedProduct = productRepository.save(product);
        ProductDto productDto = modelMapper.map(savedProduct, ProductDto.class);
        productDto.setSuccess(true);
        productDto.setMessage("Status updated successfully");
        return productDto;
    }
    @Override
    public List<ProductDto> searchProduct(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Product> products =productRepository.searchActiveProducts(query);
        return products.stream().filter(p->priceRepository.countActivePriceTypes(p.getIdentifier())==3).map(p -> modelMapper.map(p, ProductDto.class)).toList();
    }
}