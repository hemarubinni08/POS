package com.ust.pos.price.service.impl;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.price.service.PriceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        productRepository.findById(priceDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Price price = modelMapper.map(priceDto, Price.class);
        priceRepository.save(price);

        return priceDto;
    }

    @Override
    public PriceDto updatePrice(PriceDto priceDto) {

        Price price = priceRepository.findById(priceDto.getId())
                .orElseThrow(() -> new RuntimeException("Price record not found"));

        modelMapper.map(priceDto, price);
        priceRepository.save(price);

        return priceDto;
    }

    @Override
    public List<PriceDto> getAllPrices() {

        return priceRepository.findAll().stream().map(price -> {

            PriceDto dto = modelMapper.map(price, PriceDto.class);

            productRepository.findById(price.getProductId())
                    .ifPresent(p -> dto.setProductName(p.getName()));

            return dto;

        }).toList();
    }

    @Override
    public boolean deletePrice(Long id) {
        if (!priceRepository.existsById(id)) {
            return false;
        }
        priceRepository.deleteById(id);
        return true;
    }
}