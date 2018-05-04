package com.waben.stock.datalayer.stockoption.reference.fallback;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.waben.stock.datalayer.stockoption.reference.CapitalAccountReference;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.admin.publisher.CapitalAccountAdminDto;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.admin.publisher.CapitalAccountAdminQuery;

@Component
public class CapitalAccountReferenceFallback implements CapitalAccountReference {

	@Override
	public Response<PageInfo<CapitalAccountDto>> pages(CapitalAccountQuery publisherQuery) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> fetchByPublisherSerialCode(String serialCode) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> fetchByPublisherId(Long publisherId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> serviceFeeAndReserveFund(Long publisherId, Long buyRecordId,
			BigDecimal serviceFee, BigDecimal reserveFund, BigDecimal deferredFee) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<FrozenCapitalDto> fetchFrozenCapital(Long publisherId, Long buyRecordId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> deferredCharges(Long publisherId, Long buyRecordId, BigDecimal deferredCharges) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> returnReserveFund(Long publisherId, Long buyRecordId, String buyRecordSerialCode,
			BigDecimal profitOrLoss) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<Void> modifyPaymentPassword(Long publisherId, String paymentPassword) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> fetchById(Long id) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> revoke(Long publisherId, Long buyRecordId, BigDecimal serviceFee,
			BigDecimal deferredFee) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> modifyCapitalAccount(CapitalAccountDto capitalAccountDto) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> returnDeferredFee(Long publisherId, Long buyRecordId, BigDecimal deferredFee) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> rightMoney(Long publisherId, Long optionTradeId, BigDecimal rightMoney) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> returnRightMoney(Long publisherId, Long optionTradeId, BigDecimal rightMoney) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> optionProfit(Long publisherId, Long optionTradeId, BigDecimal profit) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> modifyState(Long id, Integer state) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> modifyAccount(Long staff, Long id, BigDecimal availableBalance) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> recharge(Long publisherId, BigDecimal amount, Long rechargeId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> withdrawals(Long publisherId, Long withdrawalsId, String withdrawalsStateIndex) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<CapitalAccountDto> csa(Long publisherId, BigDecimal amount, Long withdrawalsId) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

	@Override
	public Response<PageInfo<CapitalAccountAdminDto>> adminPagesByQuery(CapitalAccountAdminQuery query) {
		throw new NetflixCircuitException(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION);
	}

}
