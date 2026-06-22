package com.ust.pos.rack.service.impl;

import com.ust.pos.base.service.BaseService;
import com.ust.pos.dto.RackDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.model.Rack;
import com.ust.pos.model.RackRepository;
import com.ust.pos.rack.service.RackService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class RackServiceImpl extends BaseService implements RackService {

    public static final String RACK_NOT_FOUND = "Rack not found";

    private final RackRepository rackRepository;
    private final ModelMapper modelMapper;

    public RackServiceImpl(RackRepository rackRepository, ModelMapper modelMapper) {
        this.rackRepository = rackRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RackDto save(RackDto rackDto) {
        if (rackDto.getName() == null || rackDto.getName().trim().isEmpty()) {
            RackDto dto = new RackDto();
            dto.setSuccess(false);
            dto.setMessage("Rack name is required");
            return dto;
        }

        String name = rackDto.getName().trim();

        Rack existing = rackRepository.findByIdentifier(name);
        if (existing != null) {
            RackDto dto = new RackDto();
            dto.setSuccess(false);
            dto.setMessage("Rack already exists");
            return dto;
        }

        Rack rack = modelMapper.map(rackDto, Rack.class);
        // name and identifier must always stay in sync
        rack.setName(name);
        rack.setIdentifier(name);

        setCreatedDetails(rack);
        Rack saved = rackRepository.save(rack);

        RackDto response = modelMapper.map(saved, RackDto.class);
        response.setSuccess(true);
        response.setMessage("Rack saved successfully");
        return response;
    }

    @Override
    public RackDto update(RackDto rackDto) {
        if (rackDto.getIdentifier() == null || rackDto.getIdentifier().trim().isEmpty()) {
            RackDto dto = new RackDto();
            dto.setSuccess(false);
            dto.setMessage(RACK_NOT_FOUND);
            return dto;
        }

        Rack rack = rackRepository.findByIdentifier(rackDto.getIdentifier().trim());
        if (rack == null) {
            RackDto dto = new RackDto();
            dto.setSuccess(false);
            dto.setMessage(RACK_NOT_FOUND);
            return dto;
        }

        if (rackDto.getName() != null && !rackDto.getName().trim().isEmpty()) {
            String newName = rackDto.getName().trim();
            // keep name and identifier in sync on rename too
            rack.setName(newName);
            rack.setIdentifier(newName);
        }

        if (rackDto.getStatus() != null) {
            rack.setStatus(rackDto.getStatus());
        }

        if (rackDto.getShelfIdentifiers() != null) {
            rack.setShelfIdentifiers(rackDto.getShelfIdentifiers());
        }

        setModifiedDetails(rack);
        Rack saved = rackRepository.save(rack);

        RackDto response = modelMapper.map(saved, RackDto.class);
        response.setSuccess(true);
        response.setMessage("Rack updated successfully");
        return response;
    }

    @Override
    public RackDto findByIdentifier(String identifier) {
        Rack rack = rackRepository.findByIdentifier(identifier);
        if (rack == null) {
            RackDto dto = new RackDto();
            dto.setSuccess(false);
            dto.setMessage(RACK_NOT_FOUND);
            return dto;
        }
        RackDto dto = modelMapper.map(rack, RackDto.class);
        dto.setSuccess(true);
        return dto;
    }

    @Override
    public WsDto<RackDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<RackDto>>() {
        }.getType();
        Page<Rack> rackPage = rackRepository.findAll(pageable);

        WsDto<RackDto> rackDtoWsDto = new WsDto<>();
        rackDtoWsDto.setDtoList(modelMapper.map(rackPage.getContent(), listType));
        rackDtoWsDto.setTotalRecords(rackPage.getTotalElements());
        rackDtoWsDto.setTotalPages(rackPage.getTotalPages());
        rackDtoWsDto.setSizePerPage(pageable.getPageSize());
        rackDtoWsDto.setPage(pageable.getPageNumber());

        return rackDtoWsDto;
    }

    @Override
    public List<RackDto> getActiveRacks() {
        List<Rack> racks = rackRepository.findByStatusTrue();
        Type type = new TypeToken<List<RackDto>>() {}.getType();
        return modelMapper.map(racks, type);
    }

    @Override
    public void delete(String identifier) {
        rackRepository.deleteByIdentifier(identifier);
    }

    @Override
    public RackDto toggleStatus(String identifier) {
        Rack rack = rackRepository.findByIdentifier(identifier);
        if (rack == null) {
            RackDto dto = new RackDto();
            dto.setSuccess(false);
            dto.setMessage(RACK_NOT_FOUND);
            return dto;
        }
        rack.setStatus(!Boolean.TRUE.equals(rack.getStatus()));
        setModifiedDetails(rack);
        Rack saved = rackRepository.save(rack);
        RackDto dto = modelMapper.map(saved, RackDto.class);
        dto.setSuccess(true);
        dto.setMessage("Status updated successfully");
        return dto;
    }
}