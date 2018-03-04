package com.waben.stock.datalayer.stockoption.repository.impl;

import com.waben.stock.datalayer.stockoption.entity.OfflineStockOptionTrade;
import com.waben.stock.datalayer.stockoption.repository.OfflineStockOptionTradeDao;
import com.waben.stock.datalayer.stockoption.repository.impl.jpa.OfflineStockOptionTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OfflineStockOptionTradeDaoImpl implements OfflineStockOptionTradeDao {

    @Autowired
    private OfflineStockOptionTradeRepository offlineStockOptionTradeRepository;
    @Override
    public OfflineStockOptionTrade create(OfflineStockOptionTrade offlineStockOptionTrade) {
        return offlineStockOptionTradeRepository.save(offlineStockOptionTrade);
    }

    @Override
    public void delete(Long id) {
        offlineStockOptionTradeRepository.delete(id);
    }

    @Override
    public OfflineStockOptionTrade update(OfflineStockOptionTrade offlineStockOptionTrade) {
        return offlineStockOptionTradeRepository.save(offlineStockOptionTrade);
    }

    @Override
    public OfflineStockOptionTrade retrieve(Long id) {
        return offlineStockOptionTradeRepository.findById(id);
    }

    @Override
    public Page<OfflineStockOptionTrade> page(int page, int limit) {
        return null;
    }

    @Override
    public Page<OfflineStockOptionTrade> page(Specification<OfflineStockOptionTrade> specification, Pageable pageable) {
        return offlineStockOptionTradeRepository.findAll(specification,pageable);
    }

    @Override
    public List<OfflineStockOptionTrade> list() {
        return offlineStockOptionTradeRepository.findAll();
    }
}
