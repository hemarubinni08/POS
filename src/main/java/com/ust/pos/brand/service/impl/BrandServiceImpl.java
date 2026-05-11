package com.ust.pos.brand.service.impl;

import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.model.Brand;
import com.ust.pos.model.BrandRepository;
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
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public BrandDto save(BrandDto brandDto) {
        String identifier = brandDto.getIdentifier();
        if (brandRepository.existsByIdentifier(identifier)) {
            brandDto.setMessage("Already exists");
            brandDto.setSuccess(false);
            return brandDto;
        }
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brandRepository.save(brand);
        return brandDto;
    }

    @Override
    public List<BrandDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<BrandDto>>() {
        }.getType();
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        return modelMapper.map(brandPage.getContent(), listType);
    }

    @Override
    @Transactional
    public boolean delete(String identifier) {
        brandRepository.deleteByIdentifier(identifier);
        return true;
    }

    @Override
    public BrandDto findByIdentifier(String identifier) {
        Brand brand = brandRepository.findByIdentifier(identifier);
        return modelMapper.map(brand, BrandDto.class);
    }

    @Override
    public BrandDto update(BrandDto brandDto) {
        Brand brand = brandRepository.findByIdentifier(brandDto.getIdentifier());
        modelMapper.map(brandDto, brand);
        brandRepository.save(brand);
        return brandDto;
    }

    @Override
    public List<BrandDto> findAllActive() {
        Type listType = new TypeToken<List<BrandDto>>() {
        }.getType();
        return modelMapper.map(brandRepository.findByStatus(true), listType);
    }

    @Override
    public BrandDto changeBrandStatus(String identifier, boolean status) {
        Brand brand = brandRepository.findByIdentifier(identifier);

        if (brand != null) {
            brand.setStatus(status);
            brandRepository.save(brand);
        }
        return modelMapper.map(brand, BrandDto.class);
    }

    @Override
    public List<BrandDto> findAllForHome() {
        Type listType = new TypeToken<List<BrandDto>>() {
        }.getType();
        return modelMapper.map(brandRepository.findAll(), listType);
    }
}
