package com.ust.pos.rack.service.impl;

import com.ust.pos.dto.RackDto;
import com.ust.pos.model.Rack;
import com.ust.pos.model.RackRepository;
import com.ust.pos.rack.service.RackService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RackServiceImpl implements RackService {

    public static final String RACK_NOT_FOUND = "Rack not found";

    @Autowired
    private RackRepository rackRepository;

    // SAVE
    @Override
    public RackDto save(RackDto dto) {

        RackDto response = new RackDto();

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            response.setSuccess(false);
            response.setMessage("Rack name is required");
            return response;
        }

        // identifier = name
        Rack existing = rackRepository.findByIdentifier(dto.getName());

        if (existing != null) {
            response.setSuccess(false);
            response.setMessage("Rack already exists");
            return response;
        }

        Rack rack = new Rack();
        rack.setIdentifier(dto.getName());
        rack.setName(dto.getName());

        // FIXED BOOLEAN LOGIC (Sonar-safe)
        rack.setStatus(dto.getStatus() == null || Boolean.TRUE.equals(dto.getStatus()));

        if (dto.getShelfIdentifiers() != null && !dto.getShelfIdentifiers().isEmpty()) {
            rack.setShelfIdentifiers(String.join(",", dto.getShelfIdentifiers()));
        }

        rackRepository.save(rack);

        response.setIdentifier(rack.getIdentifier());
        response.setName(rack.getName());
        response.setStatus(rack.getStatus());
        response.setShelfIdentifiers(dto.getShelfIdentifiers());
        response.setSuccess(true);
        response.setMessage("Rack saved successfully");

        return response;
    }

    // UPDATE
    @Override
    public RackDto update(RackDto dto) {

        RackDto response = new RackDto();

        if (dto.getIdentifier() == null) {
            response.setSuccess(false);
            response.setMessage("Identifier missing");
            return response;
        }

        Rack rack = rackRepository.findByIdentifier(dto.getIdentifier());

        if (rack == null) {
            response.setSuccess(false);
            response.setMessage(RACK_NOT_FOUND);
            return response;
        }

        // Only allowed updates
        if (dto.getStatus() != null) {
            rack.setStatus(dto.getStatus());
        }

        if (dto.getShelfIdentifiers() != null) {
            rack.setShelfIdentifiers(String.join(",", dto.getShelfIdentifiers()));
        }

        rackRepository.save(rack);

        response.setIdentifier(rack.getIdentifier());
        response.setName(rack.getName());
        response.setStatus(rack.getStatus());

        if (rack.getShelfIdentifiers() != null && !rack.getShelfIdentifiers().isEmpty()) {
            response.setShelfIdentifiers(Arrays.asList(rack.getShelfIdentifiers().split(",")));
        }

        response.setSuccess(true);
        response.setMessage("Rack updated successfully");

        return response;
    }

    // FIND BY IDENTIFIER
    @Override
    public RackDto findByIdentifier(String identifier) {

        RackDto dto = new RackDto();

        if (identifier == null) {
            dto.setSuccess(false);
            dto.setMessage("Identifier missing");
            return dto;
        }

        Rack rack = rackRepository.findByIdentifier(identifier);

        if (rack == null) {
            dto.setSuccess(false);
            dto.setMessage(RACK_NOT_FOUND);
            return dto;
        }

        dto.setIdentifier(rack.getIdentifier());
        dto.setName(rack.getName());
        dto.setStatus(rack.getStatus());

        if (rack.getShelfIdentifiers() != null && !rack.getShelfIdentifiers().isEmpty()) {
            dto.setShelfIdentifiers(Arrays.asList(rack.getShelfIdentifiers().split(",")));
        }

        dto.setSuccess(true);
        return dto;
    }

    // FIND ALL
    @Override
    public List<RackDto> findAll() {

        List<Rack> racks = rackRepository.findAll();
        List<RackDto> list = new ArrayList<>();

        for (Rack r : racks) {

            RackDto dto = new RackDto();

            dto.setIdentifier(r.getIdentifier());
            dto.setName(r.getName());
            dto.setStatus(r.getStatus());

            if (r.getShelfIdentifiers() != null && !r.getShelfIdentifiers().isEmpty()) {
                dto.setShelfIdentifiers(Arrays.asList(r.getShelfIdentifiers().split(",")));
            }

            list.add(dto);
        }

        return list;
    }

    // ACTIVE RACKS
    @Override
    public List<RackDto> getActiveRacks() {

        List<RackDto> active = new ArrayList<>();

        for (RackDto r : findAll()) {
            if (Boolean.TRUE.equals(r.getStatus())) {
                active.add(r);
            }
        }

        return active;
    }

    // DELETE
    @Override
    public void delete(String identifier) {
        rackRepository.deleteByIdentifier(identifier);
    }

    // TOGGLE STATUS
    @Override
    public RackDto toggleStatus(String identifier) {

        RackDto dto = new RackDto();

        Rack rack = rackRepository.findByIdentifier(identifier);

        if (rack == null) {
            dto.setSuccess(false);
            dto.setMessage(RACK_NOT_FOUND);
            return dto;
        }

        rack.setStatus(!Boolean.TRUE.equals(rack.getStatus()));
        rackRepository.save(rack);

        dto.setSuccess(true);
        dto.setMessage("Status updated");

        return dto;
    }
}