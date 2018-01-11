package com.waben.stock.interfaces.pojo.query;

public class StrategyUnwindQuery extends PageAndSortQuery{
    private Long publisherId;
    
    private String publisherPhone;
    
    private String stockName;
    
    private String investorName;
    
    private String buyBeginTime;
    
    private String buyEndTime;
    
    private String sellBeginTime;
    
    private String sellEndTime;

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getBuyBeginTime() {
		return buyBeginTime;
	}

	public void setBuyBeginTime(String buyBeginTime) {
		this.buyBeginTime = buyBeginTime;
	}

	public String getBuyEndTime() {
		return buyEndTime;
	}

	public void setBuyEndTime(String buyEndTime) {
		this.buyEndTime = buyEndTime;
	}

	public String getSellBeginTime() {
		return sellBeginTime;
	}

	public void setSellBeginTime(String sellBeginTime) {
		this.sellBeginTime = sellBeginTime;
	}

	public String getSellEndTime() {
		return sellEndTime;
	}

	public void setSellEndTime(String sellEndTime) {
		this.sellEndTime = sellEndTime;
	}
    
}
