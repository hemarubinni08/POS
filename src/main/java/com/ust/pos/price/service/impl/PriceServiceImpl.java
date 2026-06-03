package com.ust.pos.price.service.impl;

import com.ust.pos.dto.PriceDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.model.Price;
import com.ust.pos.model.PriceRepository;
import com.ust.pos.price.service.PriceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class PriceServiceImpl implements PriceService {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PriceDto save(PriceDto priceDto) {
        String identifier = priceDto.getIdentifier();
        Price existingPrice = priceRepository.findByIdentifier(identifier);
        if(existingPrice != null)
        {
            priceDto.setMessage("Price for product - "+identifier+" already exists");
            priceDto.setSuccess(false);
            return priceDto;
        }
        priceDto.setDifference(priceDto.getSellingPrice().subtract(priceDto.getCostPrice()));
        Price price = modelMapper.map(priceDto, Price.class);
        priceRepository.save(price);
        return priceDto;
    }

    @Override
    public PriceDto update(PriceDto priceDto) {
        String identifier = priceDto.getIdentifier();
        Price existingPrice = priceRepository.findByIdentifier(identifier);
        if(existingPrice == null)
        {
            priceDto.setMessage("Price for product - "+identifier+" not defined");
            priceDto.setSuccess(false);
            return priceDto;
        }
        existingPrice.setDifference(priceDto.getSellingPrice().subtract(priceDto.getCostPrice()));
        modelMapper.map(priceDto, existingPrice);
        priceRepository.save(existingPrice);
        return priceDto;
    }

    @Override
    public void delete(String identifier) {
        priceRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<PriceDto> findAll() {
        Type listType = new TypeToken<List<PriceDto>>(){}.getType();
        return modelMapper.map(priceRepository.findAll(), listType);
    }

    @Override
    public WsDto<PriceDto> findAll(Pageable pageable)
    {
        Type listtype = new TypeToken<List<PriceDto>>(){}.getType();
        Page<Price> pricePage = priceRepository.findAll(pageable);

        WsDto<PriceDto> priceDtoWsDto = new WsDto<>();
        priceDtoWsDto.setDtoList(modelMapper.map(pricePage.getContent(), listtype));
        priceDtoWsDto.setTotalRecords(pricePage.getTotalElements());
        priceDtoWsDto.setTotalPage(pricePage.getTotalPages());
        priceDtoWsDto.setSizePerPage(pageable.getPageSize());
        priceDtoWsDto.setPage(pageable.getPageNumber());

        return priceDtoWsDto;
    }

    @Override
    public PriceDto findByIdentifier(String identifier) {
        return modelMapper.map(priceRepository.findByIdentifier(identifier), PriceDto.class);
    }
}
