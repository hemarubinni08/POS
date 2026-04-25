package com.ust.pos.price.service.impl;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.PriceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PriceDto save(PriceDto priceDto) {

        String identifier = priceDto.getIdentifier();
        Price existingPrice = priceRepository.findByIdentifier(identifier);

        if (existingPrice != null) {
            priceDto.setMessage("Price with identifier - " + identifier + " already exists");
            priceDto.setSuccess(false);
            return priceDto;
        }

        Price price = modelMapper.map(priceDto, Price.class);
        priceRepository.save(price);

        priceDto.setMessage("Price created successfully");
        priceDto.setSuccess(true);
        return priceDto;
    }

    @Override
    public PriceDto update(PriceDto priceDto) {

        String identifier = priceDto.getIdentifier();
        Price existingPrice = priceRepository.findByIdentifier(identifier);

        if (existingPrice == null) {
            priceDto.setMessage("Price with identifier - " + identifier + " not found");
            priceDto.setSuccess(false);
            return priceDto;
        }

        modelMapper.map(priceDto, existingPrice);
        priceRepository.save(existingPrice);

        priceDto.setMessage("Price updated successfully");
        priceDto.setSuccess(true);
        return priceDto;
    }

    @Override
    public boolean delete(String identifier) {
        priceRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public List<PriceDto> findAll() {

        Type listType = new TypeToken<List<PriceDto>>() {}.getType();

        return modelMapper.map(
                priceRepository.findAll(),
                listType
        );
    }
}