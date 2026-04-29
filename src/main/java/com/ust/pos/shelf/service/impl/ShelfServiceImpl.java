package com.ust.pos.shelf.service.impl;

import com.ust.pos.shelf.service.ShelfService;
import com.ust.pos.dto.ShelfDto;
import com.ust.pos.model.Shelf;
import com.ust.pos.model.ShelfRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional
public class ShelfServiceImpl implements ShelfService {
    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ShelfDto save(ShelfDto shelfDto) {
        String identifier = shelfDto.getIdentifier();
        Shelf existingShelf = shelfRepository.findByIdentifier(identifier);
        if(existingShelf != null)
        {
            shelfDto.setMessage("Shelf with identifier - "+ identifier + " already exists");
            shelfDto.setSuccess(false);
            return shelfDto;
        }
        Shelf shelf = modelMapper.map(shelfDto, Shelf.class);
        shelfRepository.save(shelf);
        return shelfDto;
    }

    @Override
    public ShelfDto update(ShelfDto shelfDto) {

        Shelf existingShelf =
                shelfRepository.findByIdentifier(shelfDto.getIdentifier());

        if (existingShelf == null) {
            shelfDto.setSuccess(false);
            shelfDto.setMessage("Shelf not found");
            return shelfDto;
        }

        // ✅ update existing row
        existingShelf.setDescription(shelfDto.getDescription());
        existingShelf.setStatus(shelfDto.isStatus());

        shelfRepository.save(existingShelf); // ✅ UPDATE

        return shelfDto;
    }

    @Override
    public void delete(String identifier) {
        shelfRepository.deleteByIdentifier(identifier);
    }

    @Override
    public List<ShelfDto> findAll() {
        Type listOfType = new TypeToken<List<ShelfDto>>(){}.getType();
        return modelMapper.map(shelfRepository.findAll(), listOfType);
    }

    @Override
    public ShelfDto findByIdentifier(String identifier) {
        return modelMapper.map(shelfRepository.findByIdentifier(identifier), ShelfDto.class);
    }
    public List<ShelfDto> findAllByStatus() {
        Type listType = new TypeToken<List<ShelfDto>>() {}.getType();
        List<ShelfDto> shelfDtos = modelMapper.map(shelfRepository.findAll(), listType);
        return shelfDtos.stream().filter(s->s.isStatus()).toList();
    }

    @Override
    public void toggleStatus(String identifier) {
        Shelf shelf = shelfRepository.findByIdentifier(identifier);
        shelf.setStatus(!shelf.isStatus());
        shelfRepository.save(shelf);
    }
}
