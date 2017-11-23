package com.waben.stock.applayer.tactics.dto.publisher;

import com.waben.stock.interfaces.dto.publisher.BindCardDto;
import com.waben.stock.interfaces.enums.BankType;

public class BindCardFullDto extends BindCardDto {

	@SuppressWarnings("unused")
	private String bankIconLink;

	/**
	 * 获取银行图标链接
	 * 
	 * @return 银行图标链接
	 */
	public String getBankIconLink() {
		BankType bankType = BankType.getByBank(this.getBankName());
		if (bankType == null) {
			bankType = BankType.DEFAULT;
		}
		return bankType.getIconLink();
	}

	public void setBankIconLink(String bankIconLink) {
		this.bankIconLink = bankIconLink;
	}

}
