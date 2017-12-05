package com.waben.stock.applayer.tactics.dto.stockcontent;

import com.waben.stock.applayer.tactics.retrivestock.bean.StockMarket;

/**
 * 股票行情和是否收藏
 * 
 * @author luomengan
 *
 */
public class StockMarketWithFavoriteDto extends StockMarket {

	private boolean favorite;

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

}
