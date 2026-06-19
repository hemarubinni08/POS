package com.ust.pos.product.service.impl;

import com.ust.pos.base.service.BaseService;
import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.ProductDto;
import com.ust.pos.model.Product;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.price.service.PriceService;
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
import java.util.Optional;

@Service
public class ProductServiceImpl extends BaseService implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PriceService priceService;

    @Override
    public PaginationResponseDto<ProductDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<ProductDto>>() {
        }.getType();

        if (pageable == null) {
            List<ProductDto> productDtoList = modelMapper.map(productRepository.findAll(), listType);

            productDtoList.forEach(this::enrichProductWithPrice);

            PaginationResponseDto<ProductDto> response = new PaginationResponseDto<>();
            response.setDtoList(productDtoList);
            response.setTotalRecords(productDtoList.size());
            return response;
        }

        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDto> productDtoList = modelMapper.map(productPage.getContent(), listType);

        productDtoList.forEach(this::enrichProductWithPrice);

        PaginationResponseDto<ProductDto> paginationResponseDto = new PaginationResponseDto<>();
        paginationResponseDto.setDtoList(productDtoList);
        paginationResponseDto.setPage(productPage.getNumber());
        paginationResponseDto.setSizePerPage(productPage.getSize());
        paginationResponseDto.setTotalPages(productPage.getTotalPages());
        paginationResponseDto.setTotalRecords(productPage.getTotalElements());

        return paginationResponseDto;
    }

    @Override
    public List<ProductDto> findByStatusTrue() {
        Type listType = new TypeToken<List<ProductDto>>() {
        }.getType();
        List<ProductDto> productDtoList = modelMapper.map(productRepository.findByStatusTrue(), listType);

        productDtoList.forEach(this::enrichProductWithPrice);
        return productDtoList;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        String identifier = productDto.getIdentifier();
        Product product = productRepository.findByIdentifier(identifier);
        if (product == null) {
            product = modelMapper.map(productDto, Product.class);
            setCreatedDetails(product);
            productRepository.save(product);
            productDto.setMessage("Successfully added the product");
            productDto.setSuccess(true);
        } else {
            productDto.setMessage("Product " + identifier + " already exists");
            productDto.setSuccess(false);
        }
        return productDto;
    }

    @Override
    public ProductDto findByIdentifier(String identifier) {
        Product product = productRepository.findByIdentifier(identifier);
        if (product == null) return null;

        ProductDto dto = modelMapper.map(product, ProductDto.class);

        enrichProductWithPrice(dto);
        return dto;
    }

    @Transactional
    @Override
    public ProductDto update(ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(productDto.getId());

        if (productOptional.isEmpty()) {
            productDto.setMessage("Email - " + productDto.getIdentifier() + " not found");
            productDto.setSuccess(false);
            return productDto;
        }

        Product existingProduct = productOptional.get();
        String productname = productDto.getIdentifier();

        boolean isProductnameChanged = !productname.equalsIgnoreCase(existingProduct.getIdentifier());

        if (isProductnameChanged && productRepository.findByIdentifier(productname) != null) {
            productDto.setMessage("Product " + productname + " already exists");
            productDto.setSuccess(false);
            return productDto;
        }

        productDto.setMessage("Product successfully edited");
        productDto.setSuccess(true);
        modelMapper.map(productDto, existingProduct);
        setModifiedDetails(existingProduct);
        productRepository.save(existingProduct);

        return productDto;
    }

    @Override
    @Transactional
    public ProductDto updateStatus(String identifier, boolean status) {
        ProductDto response = new ProductDto();

        Product product = productRepository.findByIdentifier(identifier);
        if (product == null) {
            response.setSuccess(false);
            response.setMessage("Product not found");
            return response;
        }
        setModifiedDetails(product);
        product.setStatus(status);
        response.setSuccess(true);
        response.setMessage("Status updated successfully");

        return response;
    }

    @Transactional
    @Override
    public void delete(String identifier) {
        productRepository.deleteByIdentifier(identifier);
    }

    private void enrichProductWithPrice(ProductDto productDto) {
        if (productDto == null || productDto.getIdentifier() == null) {
            return;
        }

        PriceDto sellingPriceDto = priceService.findByIdentifier(productDto.getIdentifier() + "Selling");
        if (sellingPriceDto != null) {
            productDto.setSellingPrice(sellingPriceDto.getValue());
        }

        PriceDto mrpDto = priceService.findByIdentifier(productDto.getIdentifier() + "Mrp");
        if (mrpDto != null) {
            productDto.setMrp(mrpDto.getValue());
        }
    }

    @Override
    public List<ProductDto> searchProduct(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Product> products = productRepository.searchActiveProducts(query);
        List<ProductDto> productDtoList = products.stream().map(p -> modelMapper.map(p, ProductDto.class)).toList();

        productDtoList.forEach(this::enrichProductWithPrice);
        return productDtoList;
    }
}
