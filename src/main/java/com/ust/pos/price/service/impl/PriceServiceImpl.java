package com.ust.pos.price.service.impl;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.PriceService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PriceDto save(PriceDto priceDto) {

        if (priceDto.getIdentifier() == null || priceDto.getIdentifier().isEmpty()) {
            priceDto.setSuccess(false);
            priceDto.setMessage("Identifier required");
            return priceDto;
        }

        Price existing = priceRepository.findByIdentifier(priceDto.getIdentifier());

        if (existing != null) {
            priceDto.setSuccess(false);
            priceDto.setMessage("Price already exists");
            return priceDto;
        }

        Price price = modelMapper.map(priceDto, Price.class);

        priceRepository.save(price);

        priceDto.setSuccess(true);
        return priceDto;
    }

    @Override
    public PriceDto update(PriceDto priceDto) {

        Price existing = priceRepository.findByIdentifier(priceDto.getIdentifier());

        if (existing == null) {
            priceDto.setSuccess(false);
            priceDto.setMessage("Price not found");
            return priceDto;
        }

        modelMapper.map(priceDto, existing);

        priceRepository.save(existing);

        priceDto.setSuccess(true);
        return priceDto;
    }

    @Override
    public PriceDto findByIdentifier(String identifier) {

        Price price = priceRepository.findByIdentifier(identifier);

        if (price == null) {
            PriceDto dto = new PriceDto();
            dto.setSuccess(false);
            dto.setMessage("Price not found");
            return dto;
        }

        PriceDto dto = modelMapper.map(price, PriceDto.class);
        dto.setSuccess(true);

        return dto;
    }

    @Override
    public List<PriceDto> findAll() {

        List<Price> prices = priceRepository.findAll();
        List<PriceDto> result = new ArrayList<>();

        for (Price price : prices) {

            PriceDto dto = modelMapper.map(price, PriceDto.class);

            result.add(dto);
        }

        return result;
    }

    @Override
    @Transactional
    public void delete(String identifier) {
        priceRepository.deleteByIdentifier(identifier);
    }
}