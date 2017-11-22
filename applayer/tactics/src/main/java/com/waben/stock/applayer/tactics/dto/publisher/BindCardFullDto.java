package com.waben.stock.applayer.tactics.dto.publisher;

import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.BankEnum;

public class BindCardFullDto extends BindCardDto {

	@SuppressWarnings("unused")
	private String bankIconLink;

	/**
	 * 获取银行图标链接
	 * 
	 * @return 银行图标链接
	 */
	public String getBankIconLink() {
		BankEnum bankEnum = BankEnum.getByBank(this.getBankName());
		if (bankEnum == null) {
			bankEnum = BankEnum.DEFAULT;
		}
		return bankEnum.getIconLink();
	}

	public void setBankIconLink(String bankIconLink) {
		this.bankIconLink = bankIconLink;
	}

}
