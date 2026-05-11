package com.ust.pos.brand.service.impl;

import com.ust.pos.brand.service.BrandService;
import com.ust.pos.dto.BrandDto;
import com.ust.pos.model.Brand;
import com.ust.pos.model.BrandRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;


@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public BrandDto save(BrandDto brandDto) {

        Brand brand = modelMapper.map(brandDto, Brand.class);
        brand.setIdentifier("BRAND-" + UUID.randomUUID()
                .toString().substring(0, 8).toUpperCase());
        Brand savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand, BrandDto.class);

    }

    @Override
    public BrandDto findById(Long id) {

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id " + id));
        return modelMapper.map(brand, BrandDto.class);
    }

    @Override
    public List<BrandDto> findAll(Pageable pageable) {

        Type listType = new TypeToken<List<BrandDto>>() {
        }.getType();
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        return modelMapper.map(brandPage.getContent(), listType);

    }

    @Override
    public BrandDto update(BrandDto brandDto) {
        Brand brand = brandRepository.findByIdentifier(brandDto.getIdentifier());

        if (brand == null) {
            brandDto.setSuccess(false);
            brandDto.setMessage("Brand not found");
            return brandDto;
        }

        modelMapper.map(brandDto, brand);
        brandRepository.save(brand);
        brandDto.setSuccess(true);
        brandDto.setMessage("Brand updated successfully");
        return brandDto;
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public BrandDto findByIdentifier(String identifier) {
        Brand brand = brandRepository.findByIdentifier(identifier);
        return modelMapper.map(brand, BrandDto.class);
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
}