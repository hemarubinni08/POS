package com.ust.pos.price.service.impl;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.PriceService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PriceDto findByIdentifier(String identifier) {
        return modelMapper.map(
                priceRepository.findByIdentifier(identifier),
                PriceDto.class
        );
    }

    @Override
    public PriceDto save(PriceDto dto) {

        Price existing = priceRepository.findByIdentifier(dto.getIdentifier());
        if (existing != null) {
            dto.setSuccess(false);
            dto.setMessage("Price already exists : " + dto.getIdentifier());
            return dto;
        }

        Price price = modelMapper.map(dto, Price.class);
        priceRepository.save(price);
        return dto;
    }

    @Override
    public PriceDto update(PriceDto dto) {

        Price existing = priceRepository.findByIdentifier(dto.getIdentifier());
        if (existing == null) {
            dto.setSuccess(false);
            dto.setMessage("Price not found : " + dto.getIdentifier());
            return dto;
        }

        modelMapper.map(dto, existing);
        priceRepository.save(existing);
        return dto;
    }

    @Transactional
    @Override
    public void delete(String identifier) {

        priceRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<PriceDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<PriceDto>>() {
        }.getType();
        Page<Price> customerPage = priceRepository.findAll(pageable);
        return modelMapper.map(customerPage.getContent(), listType);
    }

    @Override
    public PriceDto toggleStatus(String identifier) {
        Price price = priceRepository.findByIdentifier(identifier);
        price.setStatus(!price.isStatus());
        priceRepository.save(price);
        return modelMapper.map(price, PriceDto.class);
    }

    @Override
    public List<PriceDto> findIfTrue() {
        Type listType = new TypeToken<List<PriceDto>>() {
        }.getType();
        return modelMapper.map(priceRepository.findByStatusIsTrue(), listType);
    }
}