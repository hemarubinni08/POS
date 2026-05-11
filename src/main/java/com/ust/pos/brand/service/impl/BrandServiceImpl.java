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
    private BrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;

    // method to retrieve all brand records from the database
    @Override
    public List<BrandDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<BrandDto>>() {
        }.getType();
        if (pageable == null) {
            return modelMapper.map(brandRepository.findAll(), listType);
        }
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        return modelMapper.map(brandPage.getContent(), listType);
    }

    // method to retrieve all brands records that are currently active
    @Override
    public List<BrandDto> findByStatusTrue() {
        Type listType = new TypeToken<List<BrandDto>>() {
        }.getType();
        return modelMapper.map(brandRepository.findByStatusTrue(), listType);
    }

    // method to retrieve a record based on the identifier
    @Override
    public BrandDto findByIdentifier(String identifier) {
        return modelMapper.map(brandRepository.findByIdentifier(identifier), BrandDto.class);
    }

    // method to save brand details
    @Override
    public BrandDto save(BrandDto brandDto) {
        String identifier = brandDto.getIdentifier();
        Brand brand = brandRepository.findByIdentifier(identifier);
        if (brand == null) {
            brandDto.setSuccess(true);
            brandRepository.save(modelMapper.map(brandDto, Brand.class));
            brandDto.setMessage("Successfully added the brand");
            brandDto.setSuccess(true);
        } else {
            brandDto.setMessage("Brand " + identifier + " already exists");
            brandDto.setSuccess(false);
        }
        return brandDto;
    }

    // method to update the brand details
    @Override
    public BrandDto update(BrandDto brandDto) {
        String identifier = brandDto.getIdentifier();
        Brand brand = brandRepository.findByIdentifier(identifier);
        if (brand != null) {
            brandRepository.save(modelMapper.map(brandDto, Brand.class));
            brandDto.setMessage("Successfully updated the brand");
            brandDto.setSuccess(true);
            return brandDto;
        }
        brandDto.setSuccess(false);
        return brandDto;
    }

    // method for changing the status of a brand to active or inactive
    @Override
    @Transactional
    public BrandDto updateStatus(String identifier, boolean status) {
        BrandDto response = new BrandDto();

        Brand brand = brandRepository.findByIdentifier(identifier);
        if (brand == null) {
            response.setSuccess(false);
            response.setMessage("Brand not found");
            return response;
        }

        // Toggle status
        brand.setStatus(status);
        response.setSuccess(true);
        response.setMessage("Status updated successfully");

        return response;
    }

    // method to delete a record
    @Override
    @Transactional
    public void delete(String identifier) {
        brandRepository.deleteByIdentifier(identifier);
    }
}
