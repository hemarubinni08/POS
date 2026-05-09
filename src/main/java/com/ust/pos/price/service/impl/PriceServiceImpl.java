package com.ust.pos.price.service.impl;

import com.ust.pos.dto.CustomerDto;
import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Customer;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.price.service.PriceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.util.List;


@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PriceDto createPrice(PriceDto priceDto) {

        productRepository.findById(priceDto.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (priceRepository.existsByProductId(priceDto.getProductId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Price already exists for this product");
        }

        Price price = modelMapper.map(priceDto, Price.class);
        priceRepository.save(price);

        return priceDto;
    }

    @Override
    public PriceDto updatePrice(PriceDto priceDto) {

        Price price = priceRepository.findById(priceDto.getId()).orElseThrow(() -> new RuntimeException("Price record not found"));

        modelMapper.map(priceDto, price);
        priceRepository.save(price);

        return priceDto;
    }

    @Override
    public List<PriceDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<PriceDto>>() {
        }.getType();
        Page<Price> pricePage = priceRepository.findAll(pageable);
        return modelMapper.map(pricePage.getContent(), listType);
    }

    @Override
    public boolean deletePrice(Long id) {
        if (!priceRepository.existsById(id)) {
            return false;
        }
        priceRepository.deleteById(id);
        return true;
    }

    @Override
    public PriceDto getPriceById(Long id) {

        PriceDto dto = new PriceDto();

        priceRepository.findById(id).ifPresentOrElse(price -> {
            modelMapper.map(price, dto);
            dto.setSuccess(true);
        }, () -> {
            dto.setSuccess(false);
            dto.setMessage("Price not found");
        });

        return dto;
    }
}