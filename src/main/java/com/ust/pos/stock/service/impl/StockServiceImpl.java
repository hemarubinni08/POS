package com.ust.pos.stock.service.impl;

import com.ust.pos.base.service.BaseService;
import com.ust.pos.dto.StockDto;
import com.ust.pos.dto.WsDto;
import com.ust.pos.model.Stock;
import com.ust.pos.model.StockRepository;
import com.ust.pos.stock.service.StockService;
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
public class StockServiceImpl extends BaseService implements StockService {

    public static final String STOCK_NOT_FOUND = "Stock not found";

    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;

    public StockServiceImpl(StockRepository stockRepository,
                            ModelMapper modelMapper) {
        this.stockRepository = stockRepository;
        this.modelMapper = modelMapper;
    }

    private String calculateState(Stock stock) {

        if (Boolean.FALSE.equals(stock.getStatus())) {
            return "DISCONTINUED";
        }

        if (stock.getAvailableQuantity() == null
                || stock.getAvailableQuantity() <= 0) {
            return "OUT_OF_STOCK";
        }

        if (stock.getReorderLevel() != null
                && stock.getAvailableQuantity() <= stock.getReorderLevel()) {
            return "LOW_STOCK";
        }

        return "AVAILABLE";
    }

    @Override
    public StockDto save(StockDto dto) {

        dto.setIdentifier(dto.getProductIdentifier()
                + "_" + dto.getWarehouseIdentifier());

        if (stockRepository.findByIdentifier(dto.getIdentifier()) != null) {
            dto.setSuccess(false);
            dto.setMessage("Product already exists in this warehouse");
            return dto;
        }

        Stock stock = modelMapper.map(dto, Stock.class);

        if (stock.getStatus() == null) {
            stock.setStatus(true);
        }

        setCreatedDetails(stock);

        Stock saved = stockRepository.save(stock);

        StockDto response = modelMapper.map(saved, StockDto.class);
        response.setStockState(calculateState(saved));
        response.setSuccess(true);
        response.setMessage("Stock saved successfully");

        return response;
    }

    @Override
    public StockDto update(StockDto dto) {

        dto.setIdentifier(dto.getProductIdentifier()
                + "_" + dto.getWarehouseIdentifier());

        Stock existing = stockRepository.findByIdentifier(dto.getIdentifier());

        if (existing == null
                || Boolean.TRUE.equals(existing.getDeleted())) {
            StockDto error = new StockDto();
            error.setSuccess(false);
            error.setMessage(STOCK_NOT_FOUND);
            return error;
        }

        existing.setAvailableQuantity(dto.getAvailableQuantity());
        existing.setReorderLevel(dto.getReorderLevel());
        existing.setStatus(dto.getStatus());

        setModifiedDetails(existing);

        Stock saved = stockRepository.save(existing);

        StockDto response = modelMapper.map(saved, StockDto.class);
        response.setStockState(calculateState(saved));
        response.setSuccess(true);
        response.setMessage("Stock updated successfully");

        return response;
    }

    @Override
    public StockDto findByIdentifier(String identifier) {

        Stock stock = stockRepository.findByIdentifier(identifier);

        if (stock == null || Boolean.TRUE.equals(stock.getDeleted())) {
            StockDto dto = new StockDto();
            dto.setSuccess(false);
            dto.setMessage(STOCK_NOT_FOUND);
            return dto;
        }

        StockDto dto = modelMapper.map(stock, StockDto.class);
        dto.setStockState(calculateState(stock));
        dto.setSuccess(true);

        return dto;
    }

    @Override
    public WsDto<StockDto> findAll(Pageable pageable) {

        Type listType = new TypeToken<List<StockDto>>() {}.getType();

        Page<Stock> stockPage = stockRepository.findByDeletedFalse(pageable);

        WsDto<StockDto> ws = new WsDto<>();
        ws.setDtoList(modelMapper.map(stockPage.getContent(), listType));
        ws.setTotalRecords(stockPage.getTotalElements());
        ws.setTotalPages(stockPage.getTotalPages());
        ws.setSizePerPage(pageable.getPageSize());
        ws.setPage(pageable.getPageNumber());

        return ws;
    }

    @Override
    public void delete(String identifier) {

        Stock stock = stockRepository.findByIdentifier(identifier);

        if (stock == null) return;

        stock.setDeleted(true);

        setModifiedDetails(stock);

        stockRepository.save(stock);
    }

    @Override
    public StockDto toggleStatus(String identifier) {

        Stock stock = stockRepository.findByIdentifier(identifier);

        if (stock == null || Boolean.TRUE.equals(stock.getDeleted())) {
            StockDto dto = new StockDto();
            dto.setSuccess(false);
            dto.setMessage(STOCK_NOT_FOUND);
            return dto;
        }

        stock.setStatus(!Boolean.TRUE.equals(stock.getStatus()));

        setModifiedDetails(stock);

        Stock saved = stockRepository.save(stock);

        StockDto dto = modelMapper.map(saved, StockDto.class);
        dto.setStockState(calculateState(saved));
        dto.setSuccess(true);
        dto.setMessage("Stock status updated successfully");

        return dto;
    }

    @Override
    public List<StockDto> findActiveStock() {

        return stockRepository.findByStatusTrueAndDeletedFalse()
                .stream()
                .map(s -> {
                    StockDto dto = modelMapper.map(s, StockDto.class);
                    dto.setStockState(calculateState(s));
                    return dto;
                })
                .toList();
    }
}