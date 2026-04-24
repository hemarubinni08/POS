package com.ust.pos.price.service.impl;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.PriceService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PriceDto findByIdentifier(String identifier) {

        Optional<Price> price = Optional.ofNullable(priceRepository.findByIdentifier(identifier));

        if (price.isEmpty()) {
            PriceDto dto = new PriceDto();
            dto.setMessage("Price not found for identifier - " + identifier);
            dto.setSuccess(false);
            return dto;
        }

        return modelMapper.map(price.get(), PriceDto.class);
    }

    @Override
    public PriceDto save(PriceDto priceDto) {

        String identifier = priceDto.getIdentifier();

        if (identifier == null || identifier.isEmpty()) {
            priceDto.setMessage("Identifier is required");
            priceDto.setSuccess(false);
            return priceDto;
        }

        Optional<Price> existing = Optional.ofNullable(priceRepository.findByIdentifier(identifier));

        if (existing.isPresent()) {
            priceDto.setMessage("Price already exists for identifier - " + identifier);
            priceDto.setSuccess(false);
            return priceDto;
        }

        Price price = modelMapper.map(priceDto, Price.class);
        priceRepository.save(price);

        return priceDto;
    }

    @Override
    public PriceDto update(PriceDto priceDto) {

        String identifier = priceDto.getIdentifier();

        if (identifier == null || identifier.isEmpty()) {
            priceDto.setMessage("Invalid identifier");
            priceDto.setSuccess(false);
            return priceDto;
        }

        Optional<Price> existing = Optional.ofNullable(priceRepository.findByIdentifier(identifier));

        if (existing.isEmpty()) {
            priceDto.setMessage("Price not found for identifier - " + identifier);
            priceDto.setSuccess(false);
            return priceDto;
        }

        Price price = existing.get();
        modelMapper.map(priceDto, price);
        priceRepository.save(price);

        return priceDto;
    }

    @Override
    @Transactional
    public void delete(String identifier) {
        priceRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<PriceDto> findAll() {
        Type listType = new TypeToken<List<PriceDto>>() {}.getType();
        return modelMapper.map(priceRepository.findAll(), listType);
    }
}