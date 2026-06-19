package com.ust.pos.stock.service.impl;

import com.ust.pos.base.service.BaseService;
import com.ust.pos.dto.PaginationResponseDto;
import com.ust.pos.dto.StockDto;
import com.ust.pos.model.ProductRepository;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
import com.ust.pos.stock.service.StockService;
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
@Transactional
public class StockServiceImpl extends BaseService implements StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PaginationResponseDto<StockDto> findAll(Pageable pageable) {
        Type listType = new TypeToken<List<StockDto>>() {
        }.getType();
        if (pageable == null) {

            List<StockDto> stockDtoList =
                    modelMapper.map(stockRepository.findAll(), listType);

            PaginationResponseDto<StockDto> response =
                    new PaginationResponseDto<>();

            response.setDtoList(stockDtoList);
            response.setTotalRecords(stockDtoList.size());

            return response;
        }
        Page<Stock> stockPage = stockRepository.findAll(pageable);
        List<StockDto> stockDtoList = modelMapper.map(stockPage.getContent(), listType);

        PaginationResponseDto<StockDto> paginationResponseDto = new PaginationResponseDto<>();
        paginationResponseDto.setDtoList(stockDtoList);
        paginationResponseDto.setPage(stockPage.getNumber());
        paginationResponseDto.setSizePerPage(stockPage.getSize());
        paginationResponseDto.setTotalPages(stockPage.getTotalPages());
        paginationResponseDto.setTotalRecords(stockPage.getTotalElements());

        return paginationResponseDto;
    }

    public StockDto save(StockDto stockDto) {
        String identifier = stockDto.getProduct() + stockDto.getWarehouse();
        Stock stock=stockRepository.findByIdentifier(identifier);
        if(stock==null){
            stockDto.setIdentifier(identifier);
            stock=modelMapper.map(stockDto, Stock.class);
            setCreatedDetails(stock);
            stockRepository.save(stock);
            stockDto.setMessage("Successfully added the stock");
            stockDto.setSuccess(true);
        } else {
            stockDto.setMessage("Successfully added the stock");
            stockDto.setSuccess(false);
        }
        return stockDto;
    }

    public StockDto update(StockDto stockDto) {
        Stock existingStock = stockRepository.findById(stockDto.getId())
                .orElseThrow(() ->
                        new RuntimeException("Stock not found with id: " + stockDto.getId()));

        modelMapper.map(stockDto, existingStock);
        setModifiedDetails(existingStock);
        Stock updatedStock = stockRepository.save(existingStock);

        StockDto response = modelMapper.map(updatedStock, StockDto.class);
        response.setMessage("Stock updated successfully");
        response.setSuccess(true);

        return response;
    }

    public StockDto findByIdentifier(String identifier) {
        Stock stock = stockRepository.findByIdentifier(identifier);

        return modelMapper.map(stock, StockDto.class);
    }

    @Override
    public void delete(String identifier) {
        stockRepository.deleteByIdentifier(identifier);
    }
}
