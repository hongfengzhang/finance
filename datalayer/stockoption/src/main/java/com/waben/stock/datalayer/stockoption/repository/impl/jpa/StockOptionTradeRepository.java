package com.waben.stock.datalayer.stockoption.repository.impl.jpa;

import com.waben.stock.datalayer.stockoption.entity.StockOptionTrade;
import com.waben.stock.interfaces.enums.StockOptionTradeState;

import java.util.List;

public interface StockOptionTradeRepository extends CustomJpaRepository<StockOptionTrade,Long>{
    List<StockOptionTrade> findByState(StockOptionTradeState stockOptionTradeState);
}
